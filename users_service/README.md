# Tools Service

## API Routes

- __/api__
    - __/users__
        - __GET /__: Get all users (Paginated)
            - _?size_: Number of elements in the page
            - _?page_: Page number, 0 is first
            - _?sort_: Sorting criteria field
        - __GET /{id}__: Get user by id
        - __POST /__: Add a new user
            - __body__: JSON with the Tool to be added
            - _NOTE:_ A new __auth__ is also created, username and password are set to lowercase '_name_lastname_'
        - __DELETE /{id}__: Delete a user by its id
            - _NOTE:_ The user is not deleted from the database, it is rather deactivated, but it's associated __auth__
              it's deleted
        - __PUT /{id}__: Update a user item
            - __body__: JSON with the Tool with all fields
            - _NOTE:_ If user is activated from being deactivated it will create a new __auth__
        - __PATCH /{id}__: Partially update a user iem
            - __body__: JSON with the Tool with the fields to be updated
            - _NOTE:_ If user is activated from being deactivated it will create a new __auth__
    - __/cities__
        - __GET /__: Get all cities
    - __/brands__
        - __GET /__: Get all brands

## Required Environment variables

- TOOLMASTER_USERS_SERVICE_PORT
- TOOLMASTER_DATABASE_IP
- MYSQL_TCP_PORT
- MYSQL_USER
- MYSQL_PASSWORD
- SECRET_KEY