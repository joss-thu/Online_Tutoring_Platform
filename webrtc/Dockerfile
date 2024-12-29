# Use Node.js base image
FROM node:20

# Set the working directory
WORKDIR /app

# Copy package files and install dependencies
COPY package*.json ./
RUN npm install

# Copy the server file into the container
COPY webrtc-server.js ./

# Expose port 5000
EXPOSE 5000

# Command to run the WebRTC server
CMD ["node", "webrtc-server.js"]
