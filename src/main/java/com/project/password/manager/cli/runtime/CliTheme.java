package com.project.password.manager.cli.runtime;

import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CliTheme {

	private static final String ANSI_PREFIX = "\u001B[";
	private static final String ANSI_SUFFIX = "m";
	private static final String ANSI_RESET = "\u001B[0m";
	private static final Pattern ANSI_PATTERN = Pattern.compile("\\u001B\\[[;\\d]*m");
	private static final int MIN_PANEL_WIDTH = 46;
	private static final int MAX_PANEL_WIDTH = 72;
	private static final SymbolSet UNICODE_SYMBOLS = new SymbolSet(
			"────────────────────────────────────────────────────────",
			"━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━",
			"╭",
			"╮",
			"╰",
			"╯",
			"│",
			"❯",
			" · ");
	private static final SymbolSet ASCII_SYMBOLS = new SymbolSet(
			"--------------------------------------------------------",
			"========================================================",
			"+",
			"+",
			"+",
			"+",
			"|",
			">",
			" - ");
	private static String activeThemeName = "warm-retro";
	private static ThemePalette activePalette = ThemePalette.warmRetro();

	private CliTheme() {
	}

	public static void initialize(@Nullable String themeName) {
		activeThemeName = normalizeThemeName(themeName);
		activePalette = ThemePalette.resolve(activeThemeName);
	}

	@NotNull
	public static String getActiveThemeName() {
		return activeThemeName;
	}

	@NotNull
	public static List<String> supportedThemes() {
		return List.of("warm-retro", "ocean", "copper-dusk", "paper-retro", "plain");
	}

	public static boolean isSupportedTheme(@Nullable String themeName) {
		if (themeName == null || themeName.isBlank()) {
			return false;
		}
		return supportedThemes().contains(normalizeThemeName(themeName));
	}

	public static void printStartupExperience(@NotNull PrintStream out) {
		List<String> lines = startupLines();
		for (int index = 0; index < lines.size(); index++) {
			out.println(lines.get(index));
			if (index < lines.size() - 1) {
				pause(activePalette.animationDelayMs);
			}
		}
	}

	@NotNull
	public static String prompt() {
		return color(activePalette.promptPrimaryColor, true, false, "pm")
				+ color(activePalette.promptSecondaryColor, false, false, "::vault")
				+ " "
				+ color(activePalette.promptCursorColor, true, false, symbols().promptCursor)
				+ " ";
	}

	@NotNull
	public static String plainPrompt() {
		return activePalette.plainPrompt;
	}

	@NotNull
	public static String plainBanner() {
		String newline = System.lineSeparator();
		return symbols().doubleLine + newline
				+ activePalette.bannerTitle + newline
				+ activePalette.bannerSubtitle + newline
				+ activePalette.bannerTagline + newline
				+ symbols().doubleLine;
	}

	@NotNull
	public static String plainMuted(@NotNull String text) {
		return text;
	}

	@NotNull
	public static String banner() {
		return String.join(System.lineSeparator(), startupLines());
	}

	@NotNull
	public static String panel(@NotNull String heading, @NotNull String... lines) {
		String newline = System.lineSeparator();
		int width = panelWidth(heading, lines);
		StringBuilder builder = new StringBuilder();
		builder.append(panelBorder(symbols().panelTopLeft, symbols().panelTopRight, width)).append(newline);
		builder.append(panelRow(heading, width)).append(newline);
		builder.append(panelDivider(width)).append(newline);
		for (int index = 0; index < lines.length; index++) {
			builder.append(panelRow(lines[index], width));
			if (index < lines.length - 1) {
				builder.append(newline);
			}
		}
		builder.append(newline).append(panelBorder(symbols().panelBottomLeft, symbols().panelBottomRight, width));
		return builder.toString();
	}

	@NotNull
	public static String infoPanel(@NotNull String heading, @NotNull String... lines) {
		return panel(badge("info") + "  " + heading, lines);
	}

	@NotNull
	public static String successPanel(@NotNull String heading, @NotNull String... lines) {
		return panel(badge("ok") + "  " + heading, lines);
	}

	@NotNull
	public static String errorPanel(@NotNull String heading, @NotNull String... lines) {
		return panel(badge("error") + "  " + heading, lines);
	}

	@NotNull
	public static String hintPanel(@NotNull String heading, @NotNull String... lines) {
		return panel(badge("hint") + "  " + heading, lines);
	}

	@NotNull
	public static String preview(@NotNull String themeName) {
		String previousThemeName = activeThemeName;
		ThemePalette previousPalette = activePalette;
		initialize(themeName);
		String rendered = String.join(System.lineSeparator(),
				banner(),
				successPanel("Theme Sample",
						key("current") + muted(" : ") + secondary(getActiveThemeName()),
						key("prompt") + muted(" : ") + accent(prompt().trim()),
						key("status") + muted(" : ") + success("vault ready") + muted("  ·  ") + accent("entry synced"),
						key("surface") + muted(" : ") + secondary("badges, framed panels, retro contrast")),
				hintPanel("Commands",
						accent("theme list") + muted("  ·  ") + accent("theme preview paper-retro") + muted("  ·  ")
								+ accent("theme set copper-dusk")));
			activeThemeName = previousThemeName;
			activePalette = previousPalette;
			return rendered;
	}

	@NotNull
	public static String badge(@NotNull String text) {
		String capsule = "[ " + text.toUpperCase() + " ]";
		return color(activePalette.badgeColor, true, false, capsule);
	}

	@NotNull
	public static String secondary(@NotNull String text) {
		return color(activePalette.secondaryAccentColor, false, false, text);
	}

	@NotNull
	public static String title(@NotNull String text) {
		return color(activePalette.titleColor, true, false, text);
	}

	@NotNull
	public static String accent(@NotNull String text) {
		return color(activePalette.accentColor, false, false, text);
	}

	@NotNull
	public static String success(@NotNull String text) {
		return color(activePalette.successColor, true, false, text);
	}

	@NotNull
	public static String muted(@NotNull String text) {
		return color(activePalette.mutedColor, false, true, text);
	}

	@NotNull
	public static String key(@NotNull String text) {
		return color(activePalette.keyColor, true, false, text);
	}

	@NotNull
	public static String line() {
		return muted(symbols().line);
	}

	@NotNull
	public static String lineBold() {
		return color(activePalette.keyColor, false, false, symbols().doubleLine);
	}

	@NotNull
	public static String lineBold(@NotNull String startCap, @NotNull String endCap) {
		return color(activePalette.keyColor, false, false, startCap + symbols().doubleLine + endCap);
	}

	@NotNull
	private static String color(@NotNull String colorCode, boolean bold, boolean faint, @NotNull String text) {
		if (!activePalette.colorize()) {
			return text;
		}

		StringBuilder sequence = new StringBuilder(ANSI_PREFIX);
		boolean hasModifier = false;
		if (bold) {
			sequence.append("1");
			hasModifier = true;
		}
		if (faint) {
			if (hasModifier) {
				sequence.append(';');
			}
			sequence.append("2");
			hasModifier = true;
		}
		if (hasModifier) {
			sequence.append(';');
		}
		sequence.append("38;5;").append(colorCode).append(ANSI_SUFFIX);
		return sequence + text + ANSI_RESET;
	}

	@NotNull
	private static List<String> startupLines() {
		return Arrays.asList(panel(
				badge(activePalette.badgeLabel) + "  " + activePalette.bannerTitle,
				secondary(activePalette.bannerSubtitle),
				muted(activePalette.bannerTagline),
				key("hints") + muted(" : ") + accent("help") + muted(symbols().separator)
						+ accent("vault list") + muted(symbols().separator) + accent("entry search \"github\""))
				.split(System.lineSeparator()));
	}

	@NotNull
	private static String normalizeThemeName(@Nullable String themeName) {
		return themeName == null || themeName.isBlank() ? "warm-retro" : themeName.trim().toLowerCase();
	}

	@NotNull
	private static String panelDivider(int width) {
		return color(activePalette.mutedColor, false, false,
				symbols().panelVertical + repeat(symbols().lineUnit(), width + 2) + symbols().panelVertical);
	}

	@NotNull
	private static String panelRow(@NotNull String content, int width) {
		return color(activePalette.mutedColor, false, false, symbols().panelVertical + " ")
				+ content
				+ repeat(" ", Math.max(0, width - visibleLength(content)))
				+ color(activePalette.mutedColor, false, false, " " + symbols().panelVertical);
	}

	@NotNull
	private static String panelBorder(@NotNull String startCap, @NotNull String endCap, int width) {
		return color(activePalette.keyColor, false, false,
				startCap + repeat(symbols().doubleLineUnit(), width + 2) + endCap);
	}

	private static int panelWidth(@NotNull String heading, @NotNull String... lines) {
		int width = Math.max(MIN_PANEL_WIDTH, visibleLength(heading));
		for (String line : lines) {
			width = Math.max(width, visibleLength(line));
		}
		return Math.min(MAX_PANEL_WIDTH, width);
	}

	private static int visibleLength(@NotNull String text) {
		return ANSI_PATTERN.matcher(text).replaceAll("").length();
	}

	@NotNull
	private static String repeat(@NotNull String unit, int count) {
		return unit.repeat(Math.max(0, count));
	}

	@NotNull
	private static SymbolSet symbols() {
		String asciiOverride = System.getenv("PM_CLI_ASCII");
		if (asciiOverride != null && Boolean.parseBoolean(asciiOverride)) {
			return ASCII_SYMBOLS;
		}

		Charset charset = System.console() != null ? System.console().charset() : Charset.defaultCharset();
		CharsetEncoder encoder = charset.newEncoder();
		return encoder.canEncode(UNICODE_SYMBOLS.sample()) ? UNICODE_SYMBOLS : ASCII_SYMBOLS;
	}

	private static void pause(int delayMs) {
		if (delayMs <= 0) {
			return;
		}
		try {
			Thread.sleep(delayMs);
		} catch (InterruptedException interruptedException) {
			Thread.currentThread().interrupt();
		}
	}

	private static final class ThemePalette {
		private final String titleColor;
		private final String accentColor;
		private final String secondaryAccentColor;
		private final String successColor;
		private final String mutedColor;
		private final String keyColor;
		private final String badgeColor;
		private final String promptPrimaryColor;
		private final String promptSecondaryColor;
		private final String promptCursorColor;
		private final String bannerTitle;
		private final String bannerSubtitle;
		private final String bannerTagline;
		private final String badgeLabel;
		private final String plainPrompt;
		private final int animationDelayMs;
		private final boolean colorize;

		private ThemePalette(@NotNull String titleColor, @NotNull String accentColor,
				@NotNull String secondaryAccentColor, @NotNull String successColor, @NotNull String mutedColor,
				@NotNull String keyColor, @NotNull String badgeColor, @NotNull String promptPrimaryColor,
				@NotNull String promptSecondaryColor, @NotNull String promptCursorColor,
				@NotNull String bannerTitle, @NotNull String bannerSubtitle, @NotNull String bannerTagline,
				@NotNull String badgeLabel, @NotNull String plainPrompt, int animationDelayMs, boolean colorize) {
			this.titleColor = titleColor;
			this.accentColor = accentColor;
			this.secondaryAccentColor = secondaryAccentColor;
			this.successColor = successColor;
			this.mutedColor = mutedColor;
			this.keyColor = keyColor;
			this.badgeColor = badgeColor;
			this.promptPrimaryColor = promptPrimaryColor;
			this.promptSecondaryColor = promptSecondaryColor;
			this.promptCursorColor = promptCursorColor;
			this.bannerTitle = bannerTitle;
			this.bannerSubtitle = bannerSubtitle;
			this.bannerTagline = bannerTagline;
			this.badgeLabel = badgeLabel;
			this.plainPrompt = plainPrompt;
			this.animationDelayMs = animationDelayMs;
			this.colorize = colorize;
		}

		private boolean colorize() {
			return colorize;
		}

		@NotNull
		private static ThemePalette resolve(@Nullable String themeName) {
			String normalized = normalizeThemeName(themeName);
			switch (normalized) {
			case "warm-retro":
			case "amber":
				return warmRetro();
			case "ocean":
			case "cool":
				return ocean();
			case "copper-dusk":
			case "dusk":
				return copperDusk();
			case "paper-retro":
			case "light-retro":
			case "parchment":
				return paperRetro();
			case "plain":
			case "minimal":
				return plain();
			default:
				return warmRetro();
			}
		}

		@NotNull
		private static ThemePalette warmRetro() {
			return new ThemePalette("223", "221", "180", "149", "244", "180", "179", "223", "180", "221",
					"PASSWORD MANAGER CLI", "Warm retro shell with modern command framing.",
					"Vault-focused workflow with soft amber accents.", "retro dark", "password-manager: > ", 28,
					true);
		}

		@NotNull
		private static ThemePalette ocean() {
			return new ThemePalette("117", "159", "110", "121", "246", "81", "117", "81", "110", "159",
					"PASSWORD MANAGER CLI", "Clean shell with cool edges and subdued contrast.",
					"Good fit for darker terminals and long working sessions.", "ocean dark", "password-manager: > ",
					22, true);
		}

		@NotNull
		private static ThemePalette copperDusk() {
			return new ThemePalette("215", "216", "181", "150", "245", "173", "180", "173", "181", "215",
					"PASSWORD MANAGER CLI", "Copper dusk shell with a vault-workbench feel.",
					"Brass highlights, dark lacquer contrast, and quiet motion.", "copper dusk", "password-manager: > ",
					24, true);
		}

		@NotNull
		private static ThemePalette paperRetro() {
			return new ThemePalette("94", "130", "58", "64", "245", "101", "137", "94", "101", "130",
					"PASSWORD MANAGER CLI", "Light retro shell tuned for bright terminals.",
					"Parchment tones, ink-like contrast, and clean panel separation.", "retro light",
					"password-manager: > ", 18, true);
		}

		@NotNull
		private static ThemePalette plain() {
			return new ThemePalette("15", "15", "15", "15", "15", "15", "15", "15", "15", "15",
					"PASSWORD MANAGER CLI", "Plain terminal mode.", "No styling, no animation, maximum compatibility.",
					"plain", "password-manager: > ", 0, false);
		}
	}

	private static final class SymbolSet {
		private final String line;
		private final String doubleLine;
		private final String panelTopLeft;
		private final String panelTopRight;
		private final String panelBottomLeft;
		private final String panelBottomRight;
		private final String panelVertical;
		private final String promptCursor;
		private final String separator;

		private SymbolSet(@NotNull String line, @NotNull String doubleLine, @NotNull String panelTopLeft,
				@NotNull String panelTopRight, @NotNull String panelBottomLeft, @NotNull String panelBottomRight,
				@NotNull String panelVertical, @NotNull String promptCursor, @NotNull String separator) {
			this.line = line;
			this.doubleLine = doubleLine;
			this.panelTopLeft = panelTopLeft;
			this.panelTopRight = panelTopRight;
			this.panelBottomLeft = panelBottomLeft;
			this.panelBottomRight = panelBottomRight;
			this.panelVertical = panelVertical;
			this.promptCursor = promptCursor;
			this.separator = separator;
		}

		@NotNull
		private String sample() {
			return line + doubleLine + panelTopLeft + panelTopRight + panelBottomLeft + panelBottomRight + panelVertical
					+ promptCursor;
		}

		@NotNull
		private String lineUnit() {
			return line.substring(0, 1);
		}

		@NotNull
		private String doubleLineUnit() {
			return doubleLine.substring(0, 1);
		}
	}
}