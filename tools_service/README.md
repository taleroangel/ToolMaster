# Tools Service

## API Routes

- __/api__
  - __/tools__
    - __GET /__: Get all tools (Paginated)
      - _?size_: Number of elements in the page
      - _?page_: Page number, 0 is first
      - _?sort_: Sorting criteria field
    - __GET /{id}__: Get tool by id
    - __POST /__: Add a new tool
      - __body__: JSON with the Tool to be added
    - __DELETE /{id}__: Delete a tool by its id
    - __PUT /{id}__: Update a tool item
      - __body__: JSON with the Tool with all fields
    - __PATCH /{id}__: Partially update a tool iem
      - __body__: JSON with the Tool with the fields to be updated
  - __/cities__
    - __GET /__: Get all cities
  - __/brands__
    - __GET /__: Get all brands

## Required Environment variables
- TOOLMASTER_TOOLS_SERVICE_PORT
- TOOLMASTER_DATABASE_IP
- MYSQL_TCP_PORT
- MYSQL_USER
- MYSQL_PASSWORD