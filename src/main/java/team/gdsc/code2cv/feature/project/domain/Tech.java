package team.gdsc.code2cv.feature.project.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Tech {
	C("C"),
	CPP("C++"),
	CSHARP("C#"),
	JAVA("Java"),
	KOTLIN("Kotlin"),
	PYTHON("Python"),
	MOJO("Mojo"),
	GO("Go"),
	RUST("Rust"),
	HTML("HTML"),
	CSS("CSS"),
	JAVASCRIPT("JavaScript"),
	TYPESCRIPT("TypeScript"),
	SWIFT("Swift"),
	RUBY("Ruby"),
	PHP("PHP");

	private final String value;
}
