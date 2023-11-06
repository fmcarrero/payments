<h1 style="text-align: center;">
    Transfer Management System
</h1>

<div style="text-align: center;">

[![Hexagonal Architecture](https://img.shields.io/badge/Architecture-Hexagonal-40B5A4.svg)](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software))
[![Spring Boot](https://img.shields.io/badge/Framework-Spring%20Boot-6DB33F.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-336791.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Container-Docker-2496ED.svg)](https://www.docker.com/)

[![Apache 2.0 License](https://img.shields.io/badge/License-Apache%202.0-orange)](./LICENSE)
[![CI](https://github.com/fmcarrero/payments/actions/workflows/CI.yml/badge.svg?branch=main)](https://github.com/fmcarrero/payments/actions/workflows/CI.yml)
![Java 21](https://img.shields.io/badge/java-21-007396.svg)
![Gradle 8.4](https://img.shields.io/badge/gradle-8.4-02303A.svg)
</div>

This project is a Java-based application for managing transfers, recipients, and transactions, utilizing the Spring Boot framework and following the Hexagonal Architecture pattern for a clean separation of concerns and modular design.

## Project Structure

The application is organized around three main entities: `Recipient`, `Transfer`, and `Transaction`. Each entity is encapsulated within three packages representing the layers of the Hexagonal Architecture:

- `infrastructure`: Contains code related to database operations, networking, and interfacing with external systems.
- `application`: Hosts the application logic, defining use cases and orchestrating between the infrastructure and domain layers.
- `domain`: Defines the business models and core business logic.

The following diagram illustrates the Hexagonal Architecture pattern:

- ![hexagonal.webp](docs%2Fimg%2Fhexagonal.webp)

## Technologies Used

- **Programming Language:** Java 21
- **Framework:** Spring Boot
- **Build Tool:** Gradle
- **Database:** PostgreSQL
- **External Communication:** HTTP calls to external services

## Monetary Values

All monetary amounts in the system are handled as type `long`, representing the smallest currency unit (e.g., cents). For example, a value of `12345` equates to `123.45` in a currency format.

## Error Handling

The application includes robust error handling for managing exceptions gracefully, ensuring reliability and ease of debugging.

## Database Migrations

The initial database schema is provided in the `migrations` folder. These migration files are utilized within the Docker Compose setup to initialize the PostgreSQL database.

## Snowflake IDs

We use the Snowflake algorithm to generate unique identifiers (IDs) for distributed computing, providing a unique sequence that is time sortable.

## Health Check Endpoint

A `/ping` endpoint is available and can be used in deployment systems to check the health of the application.

# Transaction Query Features

## Paginated and Filtered Transaction Queries

The application supports advanced query capabilities for transactions, allowing users to retrieve a paginated list of transactions ordered by descending creation date. ensuring that the application remains responsive and that the data is easy to navigate.

### Using the Criteria Pattern

To achieve flexible querying, especially when it comes to filtering by amount and date, we've implemented the Criteria pattern. This design pattern enables us to construct modular and reusable query specifications that can be dynamically composed for varied searching requirements.

The Criteria API used in this application provides a robust and scalable approach for:

- **Sorting:** Transactions are sorted by their creation date in descending order by default, providing an immediate view of the most recent activity.
- **Filtering:** Users can specify filters for transaction amounts and dates, making it easy to drill down to the exact data set required.
- **Pagination:** The paginated results are presented in a user-friendly format, with the total number of transactions
  displayed.


## How to Use

To query transactions, use the following endpoint structure:

```curl
curl --location 'http://localhost:8080/transactions?size=20&amount=2310&created_at_start=2023-11-03T00%3A00%3A00&created_at_end=2023-11-03T14%3A59%3A59'
```