# Real-Time Paper Trading & Execution Engine

A full-stack financial simulation application that allows users to mock-trade high-volume assets (Stocks & Crypto) using live-fluctuating market data. Built with a modern, responsive dark-themed dashboard frontend.

## 🚀 Tech Stack
* **Backend:** Java 17, Spring Boot (MVC, Web, WebSocket)
* **Data Layer:** Spring Data JPA, Hibernate, H2 In-Memory Database
* **Frontend:** Thymeleaf, HTML5, CSS3
* **Concurrency:** Java Multithreading (`Runnable`, Thread pools)

## 🛠️ Core Features
* **Asynchronous Market Simulation:** A background thread service continuously updates asset ticker prices independently of the user interface.
* **Full-Stack Execution Loop:** Users can submit buy/sell market orders via an interactive UI dashboard that instantly reflects updates.
* **Risk Management Guardrails:** Order validation logic strictly enforces compliance rules, halting trades with insufficient funds or holding balances.
* **Relational Audit Trail:** Uses a One-to-Many relational schema to persistently log execution history receipts with automated database IDs and timestamps.
