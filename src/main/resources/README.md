# Resources Configuration

This project uses externalized YAML configuration files to organize settings in a modular way.  
All configuration files are located under:

```
src/main/resources/
```

The main entry point is:

```
application.yml
```

That file imports another configuration file:

```
properties.yml
```

### `properties.yml`

This file imports other YAML files for different configuration domains:

```
spring:
  config:
    import:
      - classpath:properties/security.yml
      - classpath:properties/database.yml
      - classpath:properties/cloud.yml
      - classpath:properties/admin.yml
      - classpath:properties/app.yml
```

---

## Required Files

If you clone this repository, you must create the following files under:

```
src/main/resources/properties/
```

### 1. `security.yml`

```
security:
  jwt:
    secret: "your-secret-key"
    expiration: 3600
```

### 2. `database.yml`

```
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/yourdb
    username: your-username
    password: your-password
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### 3. `cloud.yml`

```
cloud:
  aws:
    region: us-east-1
    s3:
      bucket: your-bucket-name
```

### 4. `admin.yml`

```
admin:
  user:
    username: admin
    password: admin-password
```

### 5. `app.yml`

```
app:
  name: your-app-name
  version: 1.0.0
```

---

## Setup Notes

1. Do **not** commit secrets (passwords, API keys, etc.) into source control.  
   Each developer should create their own local versions of these files.  
2. You can provide defaults for non-sensitive values in these files to help new developers get started quickly.  
3. For production, override values using environment variables or a secrets manager (e.g., AWS Secrets Manager, Vault).

---

## Quick Start

After cloning, create the `properties/` directory structure:

```
mkdir -p src/main/resources/properties
```

Then copy the template files above into that folder, filling in your own values.

