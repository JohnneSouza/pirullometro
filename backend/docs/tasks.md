# Improvement Tasks Checklist

Below is an ordered, actionable checklist covering architectural and code-level improvements for the backend service. Each item can be checked off upon completion.

1. [ ] Establish architecture overview documentation
   - [ ] Describe current layers (controller, service, repository, configuration, domain, validation) and their responsibilities
   - [ ] Add a simple component diagram and request flow in README or docs/architecture.md

2. [ ] Define and document API contracts
   - [ ] Add OpenAPI/Swagger UI by enabling quarkus-smallrye-openapi and documenting endpoints with annotations
   - [ ] Document request/response models, status codes, and error shapes

3. [ ] Standardize error handling across the API
   - [ ] Introduce an exception mapper (e.g., javax.ws.rs.ext.ExceptionMapper) to convert exceptions into consistent JSON error responses
   - [ ] Map validation errors (Bean Validation) to a predictable problem-details-like structure
   - [ ] Handle YouTube API failure scenarios with appropriate HTTP status codes and messages

4. [ ] Strengthen input validation and request handling
   - [ ] Ensure @Valid is used on controller method parameters receiving JSON bodies (e.g., VideoConversionRequest)
   - [ ] Improve YouTube URL validator to support common formats (youtu.be links, additional query params)
   - [ ] Add null/empty checks where needed in controllers and services

5. [ ] Fix brittle logic for extracting videoId in VideoServiceImpl
   - [ ] Replace substring(32) with a robust parser that extracts the v parameter (supporting multiple URL variants)
   - [ ] Add unit tests to cover multiple valid/invalid URL cases

6. [ ] Implement missing repository functionality
   - [ ] Implement getChannelLengthInformation in VideoRepository (define aggregation: average, min/max, count)
   - [ ] Add tests validating calculations with sample data

7. [ ] Make DataLoader resilient and safe
   - [ ] Guard against missing resource: check for null InputStream and log a clear warning instead of Objects.requireNonNull
   - [ ] Use try-with-resources around BufferedReader
   - [ ] Handle CSV parsing errors per-line with logging; skip bad lines instead of failing the whole load
   - [ ] Avoid duplicates on unique videoId (check existence before persist or rely on upsert/ignore)
   - [ ] Document CSV schema and location in docs

8. [ ] Improve ISO-8601 duration parsing
   - [ ] Replace custom parseDurationToSeconds with java.time.Duration parsing (e.g., Duration.parse)
   - [ ] Add tests covering hours/minutes/seconds edge cases

9. [ ] Handle empty dataset and boundary conditions
   - [ ] In getRandomVideo, handle count == 0 by returning 404 or a clear message
   - [ ] Ensure pagination usage is correct for Panache (validate page indexing and size)

10. [ ] Apply transactional boundaries appropriately
    - [ ] Annotate repository/service methods that mutate state with @Transactional
    - [ ] Review DataLoader transaction scope to minimize large transactions or batch persist

11. [ ] Enhance logging and observability
    - [ ] Add structured logs in controller/service/repository with correlation IDs where applicable
    - [ ] Expose metrics (Micrometer/SmallRye) for key operations (requests, failures, external calls, DB)
    - [ ] Add health checks (readiness/liveness) especially covering DB and YouTube API reachability

12. [ ] Improve configuration and secret management
    - [ ] Validate presence of youtube.api.key at startup with a clear error if missing
    - [ ] Document environment variable usage and profiles in README
    - [ ] Add example .env or application-local.example.yaml for developers

13. [ ] Harden external API integration (YouTube)
    - [ ] Add timeouts and retries (SmallRye Fault Tolerance) to YouTubeRestClient
    - [ ] Implement circuit breaker for sustained failures
    - [ ] Log and map external errors to user-friendly responses

14. [ ] Security and CORS
    - [ ] Define CORS policy in configuration (origins, methods) if frontend consumes API from a different origin
    - [ ] Evaluate authentication/authorization needs for mutating endpoints (if any in the future)

15. [ ] Data model enhancements
    - [ ] Consider adding indexes (videoId) and constraints aligned with usage patterns
    - [ ] Revisit column nullability and lengths; use appropriate types (e.g., timestamp for publishedAt)
    - [ ] Consider separating DTOs from entities (avoid exposing entities directly)

16. [ ] DTO mapping and response shaping
    - [ ] Introduce mappers (manual or MapStruct) to convert between entities and API DTOs
    - [ ] Ensure responses only include required fields; avoid leaking internal details

17. [ ] Performance improvements
    - [ ] Consider caching for frequent/expensive operations where appropriate (e.g., derived metrics)
    - [ ] Review query patterns and add projections/indexes as necessary

18. [ ] Testing strategy and coverage
    - [ ] Add unit tests for services (VideoServiceImpl), validators, and utilities (URL/Duration parsing)
    - [ ] Add repository tests using Panache/H2 for data operations
    - [ ] Add REST endpoint tests with QuarkusTest and RestAssured covering success and failure cases
    - [ ] Add tests for DataLoader behavior with well-formed/malformed CSV

19. [ ] Documentation improvements
    - [ ] Expand README with usage examples, endpoints summary, and run instructions (dev/prod/native)
    - [ ] Add a CONTRIBUTING.md with coding standards, commit style, and review checklist

20. [ ] Build and CI/CD enhancements
    - [ ] Add Maven plugins for quality (SpotBugs/Checkstyle/PMD) and enforce basic rules
    - [ ] Configure GitHub Actions (or similar) for build, test, and static analysis on PRs
    - [ ] Publish test reports and coverage (Jacoco) in CI

21. [ ] Containerization and deployment
    - [ ] Provide a Dockerfile (or Quarkus container build config) and document building/running the image
    - [ ] Add docker-compose or Dev Services instructions for local dependencies if needed in future
    - [ ] Add environment variable docs for container-based run

22. [ ] API versioning and deprecation strategy
    - [ ] Keep /v1 prefix and document approach for future versions and backward compatibility

23. [ ] Consistent naming and package organization
    - [ ] Ensure package names match responsibilities; move configuration clients to a dedicated client package if needed
    - [ ] Rename methods for clarity (e.g., getConvertedVideoLength -> convertVideoLengthToPirullaUnits)

24. [ ] Improve response models completeness
    - [ ] Flesh out ChannelLengthInformation with fields (avgLength, min, max, count, unit)
    - [ ] Ensure serialization works and is documented in OpenAPI

25. [ ] Frontend-backend integration notes (if applicable)
    - [ ] Document CORS and example fetch calls for the frontend
    - [ ] Provide a mock mode or stub endpoints for frontend development when YouTube API key is unavailable

26. [ ] Database schema lifecycle
    - [ ] Replace drop-and-create in non-dev profiles; use update or migrations (Flyway/Liquibase)
    - [ ] Add migration scripts for initial schema and future changes

27. [ ] Error and edge-case handling for random selection
    - [ ] Guard against Random.nextInt(0) when dataset is empty
    - [ ] Handle null fields in VideoEntity when CSV is incomplete

28. [ ] Localization and user-facing messages
    - [ ] Centralize user-visible messages and consider i18n if needed

29. [ ] Time zone and formatting
    - [ ] Store publishedAt as an Instant/OffsetDateTime and serialize using ISO-8601 with timezone awareness

30. [ ] Code cleanliness and style
    - [ ] Add formatter settings and enforce via Maven plugin (e.g., Spotless)
    - [ ] Remove dead code and commented-out tests; replace with meaningful tests

31. [ ] Resilience and rate limiting
    - [ ] Add rate limiting/throttling for endpoints if exposed publicly to prevent abuse

32. [ ] Telemetry
    - [ ] Add tracing (OpenTelemetry/Jaeger) for external calls to YouTube API and DB operations; document how to enable in dev
