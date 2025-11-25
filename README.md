# Laxmi Cheat Fund – Crypto Exchange Backend

A simplified Java + Spring Boot cryptocurrency exchange backend that simulates trading, wallet management, transfers, and transaction processing using JSON-based file storage.

---

## 📌 Overview

Laxmi Cheat Fund is designed for educational purposes, helping beginners understand crypto trading mechanics and backend system design.  
The project includes:
- Secure authentication (JWT)
- Wallet creation & deterministic address generation
- Buy/Sell trading logic
- Crypto price fetching (API or simulated)
- Wallet transfers
- JSON-based persistence
- Built with OOP principles: encapsulation, abstraction, inheritance, polymorphism

---

## 👥 Team Members
- Aangir  
- Het  
- Hemakshi  
- Yashvi  
- Aditi  

---

## 🛠 Technology Stack
- Java 21  
- Spring Boot  
- Maven  
- JSON File Storage  
- JWT Authentication  
- IntelliJ IDEA  
- Postman  

---

# 📦 Features

## 1. User Authentication
- Register users with hashed passwords  
- Login with JWT token  
- Deterministic wallet address generation  
- Delete account (auto-sell all crypto → redeem in USD → remove user)

---

## 2. Wallet System
- One wallet per user  
- Balances for USD, BTC, ETH, USDT  
- Add USD to wallet  
- Redeem all crypto into USD  

---

## 3. Crypto Price Service
- Fetch all crypto prices  
- Fetch price of a specific coin  
- Supports both real API mode and simulated price mode  

---

## 4. Buy/Sell Trading
- **Buy** crypto only if user has sufficient USD  
- **Sell** crypto and always credit the proceeds in USD  
- Logs stored in buysell.json and transactions.json  

---

## 5. Wallet-to-Wallet Transfers
- Secure crypto transfers between addresses  
- Automatically logged  

---

## 6. Transaction History
- Full BUY/SELL/TRANSFER history  
- Filterable per user  

---

# 📡 API Endpoints

## Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/register` | Register user |
| POST | `/auth/login` | Login & get JWT |
| POST | `/auth/delete` | Auto-sell crypto → convert to USD → delete profile |

---

## Wallet
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/wallet/all` | Get all balances |
| GET | `/wallet/{currency}` | Get specific coin balance |
| POST | `/wallet/create` | Create wallet |
| POST | `/wallet/add-usd` | Add USD to wallet |
| POST | `/wallet/redeem-usd` | Sell all crypto → redeem in USD |

---

## Prices
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/prices` | Get all prices |
| GET | `/prices/{currency}` | Get price of one coin |

---

## Trading
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/trade/buy` | Buy crypto (requires sufficient USD) |
| POST | `/trade/sell` | Sell crypto (USD credited) |

---

## Transfers
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/wallet/transfer` | Transfer crypto between wallets |

---

## Logs
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/transactions` | Get all user transactions |

---

# 📂 Data Storage Files

| File | Purpose |
|------|---------|
| `profile.json` | Stores user + wallet + balances |
| `transactions.json` | Stores all transfers & trade history |
| `buysell.json` | Stores Buy/Sell logs |
| `log.txt` | Append-only system events |

---

# 📘 Data Models

### User
- id  
- name  
- lastname  
- email  
- phone  
- passwordHash  
- secretKeyHash  
- address  
- balances  
- createdAt  

### Wallet
- address  
- balances (USD, BTC, ETH, USDT)  

### Transaction
- id  
- type  
- fromAddress  
- toAddress  
- currency  
- amount  
- priceAtTrade  
- timestamp  
- status  

### BuySell
- id  
- userAddress  
- action  
- currency  
- amount  
- totalUsd  
- priceUsd  
- timestamp  

---

# 🚫 Limitations
- No real blockchain integration  
- No real money  
- JSON not scalable for production  
- SHA-256 deterministic secret keys are not secure for real systems  

---


