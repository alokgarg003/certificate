# Contributing to Certificate Management System

Thanks for your interest in improving this project.

## Good Contribution Areas

- security hardening
- validation and error handling
- UI and UX improvements
- backend API cleanup
- test coverage
- documentation and setup instructions

## Workflow

1. Fork the repository
2. Create a short-lived feature branch
3. Keep your change focused
4. Include testing notes in your pull request
5. Update documentation when setup or behavior changes

## Local Setup

### Backend

```bash
cd backend
./mvnw spring-boot:run
```

### Frontend

```bash
cd web
npm install
npm start
```

Make sure your local MySQL configuration matches the backend datasource settings before running the app.
