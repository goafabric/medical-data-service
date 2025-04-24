# Postgres
- postgresql://postgres:postgres@localhost:5432/postgres
- schema has to be provided
- when asking about persons its able to automatically query the related address table


# Filesystem
# commands 
list_directory

# Config
```json
{
  "mcpServers": {
    "filesystem": {
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-filesystem",
        "/Users/andreas/Downloads/architecture-decission-records"
      ]
    }
  }
}