package de.inventivegames.utils.lang;

public enum Language {
	AUSTRALIAN_ENGLISH("Australian English", "en_AU"),
	AFRIKAANS("Afrikaans", "af_ZA"),
	ARABIC("العربية", "ar_SA"),
	BULGARIAN("Български", "bg_BG"),
	CATALAN("Català", "ca_ES"),
	CZECH("Čeština", "cs_CZ"),
	CYMRAEG("Cymraeg", "cy_GB"), //???
	DANISH("Dansk", "da_DK"),
	GERMAN("Deutsch", "de_DE"),
	GREEK("Ελληνικά", "el_GR"),
	CANADIAN_ENGLISH("Canadian English", "en_CA"),
	ENGLISH("English", "en_GB"),
	PIRATE_SPEAK("Pirate Speak", "en_PT"), //ARRRR!
	ESPERANTO("Esperanto", "eo_EO"),
	ARGENTINEAN_SPANISH("Español Argentino", "es_AR"), //these spanish languages still needs correct name, but i don't know how to name them
	SPANISH("Español", "es_ES"),
	MEXICO_SPANISH("Español México", "es_MX"),
	URUGUAY_SPANISH("Español Uruguay", "es_UY"),
	VENEZUELA_SPANISH("Español Venezuela", "es_VE"),
	ESTONIAN("Eesti", "et_EE"),
	EUSKARA("Euskara", "eu_ES"), //???
	ENGLISH1("زبان انگلیسی", "fa_IR"), //I translated this text and it was just 'English', even code inside was en_US
	FINNISH("Suomi", "fi_FI"),
	TAGALOG("Tagalog", "fil_PH"), //???
	FRENCH_CA("Français", "fr_CA"), //I think CA is from canada or something like that
	FRENCH("Français", "fr_FR"),
	GAEILGE("Gaeilge", "ga_IE"), //???
	GALICIAN("Galego", "gl_ES"),
	HEBREW("עברית", "he_IL"),
	ENGLISH2("अंग्रेज़ी", "hi_IN"), //another weird english
	CROATIAN("Hrvatski", "hr_HR"),
	HUNGARIAN("Magyar", "hu_HU"),
	ARMENIAN("Հայերեն", "hy_AM"),
	BAHASA_INDONESIA("Bahasa Indonesia", "id_ID"),
	ICELANDIC("Íslenska", "is_IS"),
	ITALIAN("Italiano", "it_IT"),
	JAPANESE("日本語", "ja_JP"),
	GEORGIAN("ქართული", "ka_GE"),
	KOREAN("한국어", "ko_KR"),
	KERNEWEK("Kernewek", "kw_GB"), //???
	ENGLISH3("अंग्रेज़ी", "ky_KG"), //another weird english
	LINGUA_LATINA("Lingua latina", "la_LA"), //???
	LETZEBUERGESCH("Lëtzebuergesch", "lb_LU"), //google translate don't know what is this :o
	LITHUANIAN("Lietuvių", "lt_LT"),
	LATVIAN("Latviešu", "lv_LV"),
	MALAY_NZ("Bahasa Melayu", "mi_NZ"), //???
	MALAY_MY("Bahasa Melayu", "ms_MY"), //???
	MALTI("Malti", "mt_MT"),
	NORWEGIAN("Norsk", "nb_NO"),
	DUTCH("Nederlands", "nl_NL"),
	NORWEGIAN_NYNORSK("Norsk nynorsk", "nn_NO"), //???
	NORWEGIAN1("Norsk", "no_NO"), //???
	OCCITAN("Occitan", "oc_FR"), //???
	PORTUGUESE_BR("Português", "pt_BR"),
	PORTUGUESE_PT("Português", "pt_PT"),
	QUENYA("Quenya", "qya_AA"), //???
	ROMANIAN("Română", "ro_RO"),
	RUSSIAN("Русский", "ru_RU"),
	ENGLISH4("Angličtina", "sk_SK"), //another weird english, i think it's slovak
	SLOVENIAN("Slovenščina", "sl_SI"),
	SERBIAN("Српски", "sr_SP"),
	SWEDISH("Svenska", "sv_SE"),
	THAI("ภาษาไทย", "th_TH"),
	tlhIngan_Hol("tlhIngan Hol", "tlh_AA"), //???
	TURKISH("Türkçe", "tr_TR"),
	UKRAINIAN("Українська", "uk_UA"),
	VIETNAMESE("Tiếng Việt", "vi_VI"),
	SIMPLIFIED_CHINESE("简体中文", "zh_CN"),
	TRADITIONAL_CHINESE("繁體中文", "zh_TW"),
	POLISH("Polski", "pl_PL");

	private String	name;
	private String	code;

	Language(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public static Language getByCode(String code) {
		for (Language l : values()) {
			if (l.getCode().equalsIgnoreCase(code))
				return l;
		}
		return null;
	}

	public static String codeByLanguage(Language lang) {
		return lang.getCode();
	}

}
