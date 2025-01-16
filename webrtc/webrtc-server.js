const express = require("express");
const http = require("http");
const { Server } = require("socket.io");

const app = express();
const server = http.createServer(app);
const io = new Server(server, {
    cors: {
        origin: "http://localhost:3000",
        methods: ["GET", "POST"],
    },
});

const clients = [];

io.on("connection", (socket) => {
    console.log("Client connected with socket id: " + socket.id);
    const userId = socket.handshake.query.userId;

    // Check for existing client and update or add
    const existingClient = clients.find(client => client.userId === userId);
    if (existingClient) {
        existingClient.socketId = socket.id;
    } else {
        clients.push({
            userId: userId,
            socketId: socket.id,
        });
    }

    socket.on("disconnect", () => {
        const index = clients.findIndex(clientSocket => clientSocket.socketId === socket.id);
        if (index !== -1) {
            clients.splice(index, 1);
        }
        console.log("Client disconnected with socket id: " + socket.id);
        socket.broadcast.emit("callEnded");
    });

    socket.on("callUser", (data) => {
        const client = clients.find(client => client.userId === data.userId);
        if (client) {
            io.to(client.socketId).emit("callUser", {
                signal: data.signalData,
                from: data.from,
                name: data.name,
            });
        } else {
            console.log("No user found for userId: " + data.userId);
            socket.emit("userNotFound", { userId: data.userId });
        }
    });

    socket.on("answerCall", (data) => {
        io.to(data.to).emit("callAccepted", data.signal);
    });
});

server.listen(5001, () => console.log("Server running on port 5001"));
