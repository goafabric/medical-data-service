# Success

- identifying a faulty call to a rest endpoint (post vs get) which slipped through by calling an undesired method
- generation if a composable TenantAwareKafkaListener

# Partial Success (multiple tries)
- changing the own TenantAwareKafkaListener to standard Kafalistener, however this needed 2 tries to get it right

# Fail
- Kafkalistener with Wildcard for all topics, Windsurf/Claude haluzinated, while chatgpt just said thats not possible
- Generating setTenantContext similiar to HttpInterceptor

# Bug
- With Windsurf installed in Intellij, when writing code, the lines are constantly shifting up and down, this is like getting see sickness on a boat

