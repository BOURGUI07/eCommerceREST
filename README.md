Here's the updated `README.md` description for your project, incorporating the latest additions:

```markdown
### User Requirement Story

#### Overview
As a client, I require a RESTful service for managing an e-commerce platform. The service should be developed using MySQL as the database and Spring Boot for the backend. The system must support complex entity relationships and enable advanced querying capabilities.

## Technologies Used
	Spring Boot: For application configuration and setup.
	Spring Web: To define REST endpoints.
	Spring Data JPA: For database interactions.
	Spring Security: To handle authentication and authorization.
	MySQL: For the relational database management system.
	Maven: For managing project dependencies.
	Postman: For testing the API endpoints.
	JUnit: For writing and running unit tests.
	Mockito: For mocking dependencies in unit tests.

#### Entities and Relationships

1. **User**
    - Attributes: `user_id`, `user_name`, `profile_id`, `createdBy`, `createdAt`
    - Relationships: 
        - One-to-One with `UserProfile`
        - One-to-Many with `Order_Items`

2. **UserProfile**
    - Attributes: `profile_id`, `email`, `address`, `phone_number`, `createdBy`, `createdAt`
    - Relationships: 
        - One-to-One with `User`

3. **Product**
    - Attributes: `product_id`, `name`, `description`, `price`, `stock`, `category_id`, `createdBy`, `createdAt`
    - Relationships:
        - Many-to-One with `Category` 

4. **Category**
    - Attributes: `category_id`, `name`, `description`, `createdBy`, `createdAt`
    - Relationships:
        - One-to-Many with `Product`

5. **Order**
    - Attributes: `order_id`, `user_id`, `order_date`, `status`, `createdBy`, `createdAt`
    - Relationships:
        - One-to-Many with `OrderItem`
        - Many-to-One with `User`

6. **OrderItem**
    - Attributes: `order_item_id`, `order_id`, `product_id`, `quantity`, `price`, `createdBy`, `createdAt`
    - Relationships:
        - Many-to-One with `Order`
        - Many-to-One with `Product`

#### RESTful Service Requirements

1. **User Management**
    - Create, update, delete, and retrieve user information.
    - Retrieve user profiles with detailed information (including profile details and orders).
    - Retrieve users based on complex filters (e.g., users who have placed orders within a date range, users with profiles that match certain criteria).

2. **Product Management**
    - Create, update, delete, and retrieve product information.
    - Retrieve products based on complex filters (e.g., products in certain categories, products within a price range, products with low stock).
    - Implement pagination and sorting in endpoints.

3. **Category Management**
    - Create, update, delete, and retrieve category information.
    - Retrieve categories with associated products.

4. **Order Management**
    - Create, update, delete, and retrieve order information.
    - Retrieve orders with detailed information (including order items and user details).
    - Retrieve orders based on complex filters (e.g., orders by status, orders within a date range, orders containing specific products).
    - Implement pagination and sorting in endpoints.

5. **Advanced Queries**
    - Retrieve users who have spent more than a specified amount in a given time period.
    - Retrieve products that have been ordered a certain number of times within a date range.
    - Retrieve categories with the highest number of products.
    - Generate reports on sales (total sales by product, total sales by category, total sales by user).

6. **Authentication and Authorization**
    - Implement user authentication using JWT.
    - Ensure certain endpoints are protected and require appropriate user roles/permissions.

7. **Performance and Scalability**
    - Ensure the service is optimized for performance, especially for complex queries.
    - Implement pagination for large datasets.

8. **Documentation and Testing**
    - Provide comprehensive API documentation.
    - Include unit tests and integration tests to ensure the reliability of the service.

#### Expected Outcomes

- A fully functional RESTful service supporting complex relationships and advanced querying capabilities.
- Efficient and scalable service suitable for an e-commerce platform.
- Secure endpoints with role-based access control.
- Thoroughly documented and tested application.


### Roles and Authorities

#### Roles

1. **Admin**
    - Description: Administrators have the highest level of access. They can manage all aspects of the system, including user management, product management, category management, and order management.
    - Authorities:
        - `MANAGE_USERS`
        - `MANAGE_PRODUCTS`
        - `MANAGE_CATEGORIES`
        - `MANAGE_ORDERS`
        - `VIEW_REPORTS`

2. **Vendor**
    - Description: Vendors can manage products and view orders related to their products. They have limited access compared to administrators and cannot manage users.
    - Authorities:
        - `MANAGE_PRODUCTS`
        - `VIEW_ORDERS`


3. **Guest**
    - Description: Guests can browse products and view limited information. They must register to place orders or view detailed product information.
    - Authorities:
        - `VIEW_PRODUCTS`

#### Authorities

1. **MANAGE_USERS**
    - Description: Allows the role to create, update, delete, and retrieve user information, including user profiles and associated details.
    - Applicable Roles: Admin

2. **MANAGE_PRODUCTS**
    - Description: Allows the role to create, update, delete, and retrieve product information.
    - Applicable Roles: Admin, Vendor

3. **MANAGE_CATEGORIES**
    - Description: Allows the role to create, update, delete, and retrieve category information.
    - Applicable Roles: Admin

4. **MANAGE_ORDERS**
    - Description: Allows the role to create, update, delete, and retrieve order information.
    - Applicable Roles: Admin

5. **VIEW_REPORTS**
    - Description: Allows the role to generate and view sales reports and other analytical data.
    - Applicable Roles: Admin

6. **VIEW_ORDERS**
    - Description: Allows the role to view order information, including detailed order items and associated user details.
    - Applicable Roles: Admin, Vendor

7. **VIEW_PRODUCTS**
    - Description: Allows the role to view product information.
    - Applicable Roles: Admin, Vendor, Customer, Guest


#### Role-Based Access Control (RBAC)

- **Admin**
    - Full access to all endpoints and functionalities.
- **Vendor**
    - Access to endpoints related to product management and order viewing.
- **Guest**
    - Access to endpoints for viewing products only. Registration is required for further access.

#### Implementation Notes

- **Endpoint Protection**: Ensure that endpoints are protected using Spring Security to enforce role-based access control.
- **Token-Based Authentication**: Implement JWT (JSON Web Token) for secure and stateless authentication.
- **Role Hierarchies**: Consider defining role hierarchies in Spring Security to simplify the assignment of authorities (e.g., `Admin` implicitly has all authorities granted to `Vendor`).
```

This should reflect the latest updates regarding entity base attributes, validation of fields, and pagination and sorting in REST endpoints. Adjust as necessary based on specific details or additional functionalities implemented.
