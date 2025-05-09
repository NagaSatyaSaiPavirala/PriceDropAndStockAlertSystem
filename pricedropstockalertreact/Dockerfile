# # Step 1: Build the React app
# FROM node:18 AS build

# WORKDIR /app
# COPY package*.json ./
# RUN npm install
# COPY . .
# RUN npm run build

# # Step 2: Serve with Nginx
# FROM nginx:alpine

# # Remove default nginx website
# RUN rm -rf /usr/share/nginx/html/*

# # Copy build files from previous step
# COPY --from=build /app/build /usr/share/nginx/html

# # Copy custom nginx config (optional)
# # COPY nginx.conf /etc/nginx/conf.d/default.conf

# EXPOSE 80

# CMD ["nginx", "-g", "daemon off;"]






# # Build Stage
# FROM node:18-alpine as build

# WORKDIR /app

# COPY package*.json ./
# RUN npm ci

# COPY . .
# RUN npm run build

# # Serve with Nginx
# FROM nginx:alpine

# COPY --from=build /app/build /usr/share/nginx/html
# COPY nginx.conf /etc/nginx/conf.d/default.conf

# EXPOSE 80
# CMD ["nginx", "-g", "daemon off;"]






# ----------- Build Stage -----------
FROM node:18-alpine AS build

WORKDIR /app

# Copy only package files first for better caching
COPY package*.json ./
RUN npm ci

# Copy the rest of the app
COPY . .

# Build the React app
RUN npm run build

# ----------- Nginx Serve Stage -----------
FROM nginx:alpine

# Optional: Clean default static assets
RUN rm -rf /usr/share/nginx/html/*

# Copy build files from previous stage
COPY --from=build /app/build /usr/share/nginx/html
# RUN ls -l /app

# Copy custom Nginx config
COPY conf/nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
