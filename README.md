
# JDBC ECommerce

A project in Java DataBase Connectivity ECommerce.


## 🛠 Technology used

 - Core Java
 - MySQL

## 🛠 Tools used

- IntelliJ
- MySQL Workbench

## Users

- Admin 
    
        *Admin can login with his/her valid credentials.

        *Here we have used one admin with email-(pm@gmail.com) and password(PM).

        *Admin can able to do the following process.
        1. Add product.
        2. Delete product.
        3. Edit products.
        4. See all products.
        5. See all users.
        6. See all orders

- User 
    
        *User can login with his/her valid credentials.

        *Here user can register if he newly visits the application and can login with their credentials.
    
        *User can able to do the following process.
        1. See category.
        2. See products.
        3. Add the products to cart.
        4. Checkout.
        



## Controller used

Controller is used to write the business logic.
- App Component

- Admin Component
- Authentication Component
- Cart Component
- Home Component
- Order Component
- Product Component


## Working of components

- App component
        
        - The Application starts here with the initial welcome message.
        - The welcome message prints and the authentication starts running.

- Authentication component

        - Login validation will be done here with matching the values in the user table in database.
        - Registration also will be done by adding the users in the user in the user table.

- Home component

        - After validation, the home page will be loaded for users, with the 5 options like,
            1. Show Category
            2. Show products
            3. Show Cart
            4. Show Orders
            5. Logout

        - After entering input(1) the categories will be loaded.

- Admin component

        - Once admin validation done, admin home page will be shown with 6 options like,
            1. Show all products
            2. Add product 
            3. Edit product
            4. Delete product
            5. See all users
            6. See all Orders
            7. Logout

- Product component

        Products will be loaded using the product table in database.

- Cart component

        - Adding products to cart page will be done here for specific users with loggedInUser.

- Order component

        - After checkout, the order details will be updated in the order table in database.


## For Storage purpose,

Used MySQL workbench for Storage purpose, Created table like, 

- Role
- User
- Category
- Product 
- Cart
- Orders 
- Logout
