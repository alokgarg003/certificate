# Certificate Management System

Secure full-stack application for managing certificate requests, approval workflows, verification, and PDF download.

This project combines a Spring Boot backend with a React frontend to support both administrator-led certificate approval and student self-service access.

## Why This Project Stands Out

- Role-based access for admin and student workflows
- JWT-based authentication and protected APIs
- Certificate approval and rejection flow
- PDF generation for downloadable certificates
- Verification-oriented workflow for academic credentials
- Full-stack implementation using Java, Spring Boot, React, and MySQL

## Core Features

### Admin Workflow

- Create and manage student accounts
- Review certificate requests
- Approve or reject certificates
- View student and certificate records from a centralized interface

### Student Workflow

- Sign in securely
- Apply for a certificate
- Recover or update credentials
- Download approved certificates

## Tech Stack

| Layer | Technology |
|---|---|
| Frontend | React, Material UI, Axios, React Router |
| Backend | Spring Boot, Spring Security, Spring Data JPA |
| Authentication | JWT |
| Database | MySQL |
| Document Generation | Flying Saucer PDF |
| Build Tools | Maven Wrapper, npm |

## Project Structure

```text
certificate/
|- backend/   Spring Boot API, business logic, security, PDF generation
|- web/       React frontend for admin and student flows
|- certificate.sql
```

## Getting Started

### Prerequisites

- Java 17
- Node.js 18+
- MySQL

### Backend

```bash
cd backend
./mvnw spring-boot:run
```

Backend runs on `http://localhost:5000`.

The current sample configuration in `backend/src/main/resources/application.properties` points to a local MySQL database named `certificate`. Update the datasource values for your environment before production use.

### Frontend

```bash
cd web
npm install
npm start
```

Frontend runs on `http://localhost:3000`.

## Screenshots

![Admin dashboard](https://github.com/user-attachments/assets/cad824b5-4156-4f37-b8e1-935c8ac449aa)
![Certificate workflow](https://github.com/user-attachments/assets/56c97054-8dae-4a4e-9d0e-2a84eedb3e51)
![Student view](https://github.com/user-attachments/assets/61df29d7-9571-45c5-a875-41b56ca9cc2b)

## What This Repo Demonstrates

- Full-stack application design
- Secure API development with Spring Security
- Business workflow modeling
- Frontend and backend integration
- Practical document generation and download handling

## Contributing

Ideas, bug fixes, documentation updates, and improvements to security, validation, UX, or developer setup are welcome.

See [CONTRIBUTING.md](CONTRIBUTING.md) for contribution guidance.

## Security

Please do not open public issues for security vulnerabilities.

See [SECURITY.md](SECURITY.md) for the reporting process.
