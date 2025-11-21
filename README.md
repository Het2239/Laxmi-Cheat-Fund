# Crypto Exchange Application

A simplified cryptocurrency exchange application built with Spring Boot.

## Features

- User Registration & Login with JWT Authentication
- Multi-currency Wallet Support (BTC, ETH, USDT, SOL)
- Buy/Sell Cryptocurrency
- Transfer Crypto between Users
- Real-time Price Tracking
- Transaction History

## Project Structure

```
crypto-exchange-backend/
├── src/main/java/com/exchange/
│   ├── controller/       # REST API Controllers
│   ├── service/          # Business Logic
│   ├── model/            # Data Models
│   ├── repository/       # JSON File Repositories
│   ├── security/         # JWT Security
│   └── utils/            # Utility Classes
├── data/                 # JSON Data Storage
└── schema/               # Sample Data Schemas
```

## Technologies

- Java
- Spring Boot
- JWT Authentication
- JSON File Storage
- SHA-256 Hashing

## Getting Started

TODO: Add build and run instructions

## API Endpoints

### Authentication
- POST /auth/register
- POST /auth/login

### Wallet
- POST /wallet/create
- GET /wallet/all
- GET /wallet/{currency}

### Prices
- GET /prices
- GET /prices/{currency}

### Trading
- POST /trade/buy
- POST /trade/sell

### Transactions
- POST /wallet/transfer
- GET /transactions

## License

TODO: Add license information
