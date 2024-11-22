# 🛒 E-Commerce Website

A modern, fully-functional e-commerce platform showcasing a seamless shopping experience, built with **Angular**, **Tailwind CSS**, and **DaisyUI** for the frontend, and powered by **Spring Boot** and **PostgreSQL** on the backend.

This project demonstrates advanced full-stack development skills, user authentication with **Kinde Auth**, and secure payments using **Stripe**.

---

## 🚀 Live Demo

- **YouTube Demo Video**: [Watch Here](https://www.youtube.com/watch?v=GeHtXE93yKM)

---

## 📸 Screenshots

| Home Page                              | Product List                            |
|----------------------------------------|-----------------------------------------|
| *(Insert screenshot of the home page)* | *(Insert screenshot of the product list page)* |

| Product Details                        | Checkout                                |
|----------------------------------------|-----------------------------------------|
| *(Insert screenshot of product page)*  | *(Insert screenshot of checkout page)* |

---

## 🛠️ Features

- **User Authentication**: Single Sign-On (SSO) with Kinde, providing secure and seamless access.
- **Product Management**: Comprehensive product and category handling.
- **Shopping Cart**: Add, update, or remove items with real-time updates.
- **Stripe Payments**: Fully integrated, secure payment gateway.
- **Responsive Design**: Mobile-first design with Tailwind CSS and DaisyUI for modern UI/UX.
- **Order Management**: Dynamic updates based on successful payments.

---

## 🔧 Tech Stack

### **Frontend**
- Angular 18
- Tailwind CSS
- DaisyUI

### **Backend**
- Spring Boot 3
- PostgreSQL
- Stripe API for payments

### **Authentication**
- Kinde SSO (OAuth2)

---

## 📚 Installation Guide

### **Prerequisites**
- Node.js and npm
- Angular CLI
- Java (JDK 21+)
- PostgreSQL
- Docker

### **Setup**

#### 1. Clone the repository
```bash
git clone https://github.com/John-Mejja/Ecommerce-Website.git
cd Ecommerce-Website
```

#### 2. Backend Setup
- Navigate to the backend directory:
  ```bash
  cd apps/ecom-backend
  ```
- Configure `application.properties`:
  - Set up PostgreSQL database credentials.
  - Add your Kinde Auth and Stripe API keys.
- Build and run the backend:
  ```bash
  ./mvnw spring-boot:run
  ```

#### 3. Frontend Setup
- Navigate to the frontend directory:
  ```bash
  cd apps/ecom-frontend
  ```
- Install dependencies:
  ```bash
  npm install
  ```
- Start the application:
  ```bash
  ng serve
  ```

#### 4. Access the application
- Visit `http://localhost:4200` in your browser.

---

## 💳 Kinde Auth Configuration

To enable authentication:
1. Set up a Kinde Auth account at [Kinde Auth](https://kinde.com/).
2. Set up user authentication for frontend and backend application.
3. Generate and set your API keys in the backend `application.properties` file.
4. Generate and set your API keys in the frontend 'environment.ts' file.
5. Test the integration.

---

## 💳 Stripe Configuration

To enable Stripe payments:
1. Set up a Stripe account at [Stripe](https://stripe.com).
2. Generate and set your API keys in the backend `application.properties`.
3. Test the integration using Stripe's test cards.

---

## 📼 Video Demonstration

- Watch the project in action on YouTube: [https://www.youtube.com/watch?v=GeHtXE93yKM](https://www.youtube.com/watch?v=GeHtXE93yKM)

---

## 🔍 Learn More

### Terminologies Used:
- [Angular](https://angular.dev/)
- [Tailwind CSS](https://tailwindcss.com/docs/guides/angular)
- [DaisyUI](https://daisyui.com/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [PostgreSQL](https://www.postgresql.org)
- [Kinde Auth](https://kinde.com/)
- [Stripe API](https://dashboard.stripe.com/login)

---

## 👥 Contributors

- **[John Major](https://github.com/John-Mejja)**  
  *Full-Stack Developer*

---


