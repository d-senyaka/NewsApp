# NewsApp

**NewsApp** is a Java-based application for working with news content, with a small amount of styling written in CSS.  
(Repository languages: **Java ~92%**, **CSS ~8%**)

Repo: https://github.com/d-senyaka/NewsApp

---

## What this repo contains

- **Java source code** that implements the core application logic
- **CSS assets** used for styling the UI (for example, web pages/views)

---

## Requirements

- **Java** (JDK 8+ recommended; use the version required by your project)
- A build tool, depending on how the project is set up:
  - **Maven**, if the project uses `pom.xml`
  - **Gradle**, if the project uses `build.gradle` / `build.gradle.kts`
  - Or plain `javac/java` if no build tool is used

---

## Getting Started

### 1) Clone the repository

```bash
git clone https://github.com/d-senyaka/NewsApp.git
cd NewsApp
```

### 2) Build & Run (choose the one that matches your project)

#### If this is a Maven project
```bash
mvn clean package
mvn exec:java
```

#### If this is a Gradle project
```bash
./gradlew build
./gradlew run
```

#### If this is a plain Java project
Compile (example):
```bash
javac -d out $(find . -name "*.java")
```

Run (replace with your actual main class):
```bash
java -cp out com.example.Main
```

---

## Configuration

If the app requires configuration (API keys, base URLs, etc.), prefer **environment variables** or an **untracked config file** instead of hardcoding secrets.

Examples (adjust names to your project):

```bash
export NEWS_API_KEY="your_key_here"
export NEWS_API_BASE_URL="https://example.com"
```

---

## CSS / UI Assets

CSS files are included for styling. If this is a web UI, they’re typically referenced from HTML/templates.  
If this is a desktop/server UI, they may be used by the UI layer your project uses.

---

## Contributing

1. Fork the repo
2. Create a feature branch: `git checkout -b feature/my-change`
3. Commit your changes: `git commit -m "Describe change"`
4. Push: `git push origin feature/my-change`
5. Open a Pull Request

---

## Author

- GitHub: [@d-senyaka](https://github.com/d-senyaka)
