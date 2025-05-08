# Success

- identifying a faulty call to a rest endpoint (post vs get) which slipped through by calling an undesired method
- generation if a composable TenantAwareKafkaListener
- setting up kotlin project similar to java with almost no changes to rules file

# Partial Success (multiple tries)
- changing the own TenantAwareKafkaListener to standard Kafalistener, however this needed 2 tries to get it right

# Fail
- Kafkalistener with Wildcard for all topics, Windsurf/Claude haluzinated, while chatgpt just said thats not possible
- Generating setTenantContext similiar to HttpInterceptor
- Even with a precise windsurf.rules file, with examples, it failed to create a rest client integration test, when asking to fix, it gave up and started mocking things
- Failed miserable for creating simple tests for the project it created, due to simple constructor parameter errors

- Integration of "human code guidelines" via MCP, the guidelines are to unspecific in constrast to explicit .windsurfrules to produce reasonable results  

# Bug
- With Windsurf installed in Intellij, when writing code, the lines are constantly shifting up and down, this is li
- Intellij plugin bugging out a lot with "a call to a tool failed" since latest updates

