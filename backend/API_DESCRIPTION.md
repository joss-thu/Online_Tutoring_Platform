# API Documentation
## Authentication Endpoint

#### `POST /auth/login`
- **Description**:This endpoint authenticates users based on their credentials. 
- Upon successful authentication, a JSON Web Token (JWT) is issued, along with timestamps for token creation and expiration.
- **Request Body**:
    - `LoginRequestTO`: The authentication request
- **Throws**:
    - Exception if provided credentials does not match a user in database
- **Response**: Authentication response as `LoginResponseTO`
- **To Do**:
---

#### `POST /auth/register`
- **Description**:This endpoint registers users based on their credentials.
- Upon successful authentication, a JSON Web Token (JWT) is issued, along with timestamps for token creation and expiration.
- **Request Body**:
    - `LoginRegisterTO`: The authentication request
- **Throws**:
    - Exception if a user with the same email and role already exists.
    - Same credentials but with another role is allowed.
- **Response**: Authentication response as `LoginResponseTO`
- **To Do**:
---

## Admin Endpoints
### Users
#### `POST /...`