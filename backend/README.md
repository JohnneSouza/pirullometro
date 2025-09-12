# pirullometro

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/pirullometro-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- REST Jackson ([guide](https://quarkus.io/guides/rest#json-serialisation)): Jackson serialization support for Quarkus REST. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)


## GitHub: Creating Pull Requests via API

See docs/github-pull-requests.md for details on how to open a pull request using GitHub's REST API, including example curl and an optional MicroProfile REST client interface.

## GitHub Personal Access Token (PAT) permissions required

The flows referenced in this project include:
- Updating a file via the REST Contents API (commit to a branch)
- Optionally creating a new branch and then
- Opening a pull request from that branch

Depending on whether you use a classic PAT or a fine‑grained PAT, grant the following permissions.

Classic PAT scopes:
- repo — full control of private repositories (includes contents read/write and pull request write). Required for private repositories.
  - If working only with public repositories, public_repo is sufficient.
- Optional (only if you use the low‑level Git data API to create refs/trees/commits instead of the Contents API): repo scope already covers this; no extra scope needed.

Fine‑grained PAT permissions:
- Repository access: grant access to the specific repository (or repositories) you will modify.
- Permissions:
  - Contents: Read and write — required to GET/PUT file contents and create commits on a branch.
  - Pull requests: Read and write — required to create (open) pull requests.
  - Metadata: Read — typically implied; allows the API to read basic repo info.
  - Optional (only if using Git data API to create branches/refs directly):
    - Contents: Read (still needed) and possibly Administration: Read if you are manipulating protected branches. For standard ref creation via Git data API, Contents read/write plus Repo access is usually sufficient.

Notes and tips:
- If you commit directly to main, ensure your branch is not protected or that your token/account meets the branch protection requirements (e.g., bypass required reviews). Otherwise, create a feature branch and open a PR.
- For cross‑fork pull requests, PR creation requires Pull requests: write on the destination repository, and the source branch must exist and be accessible.
- If you receive 401/403 errors: verify the token hasn’t expired, has not been revoked, and that the repository selection and permissions match the target repo.
- If you receive 409/422 on update file: fetch the latest file sha and retry; make sure base/head branches are valid and distinct for PRs.
