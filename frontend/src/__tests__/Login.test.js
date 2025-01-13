import { render, screen, fireEvent } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import Login from '../pages/Login';

jest.mock('../api/auth', () => ({
    login: jest.fn(),
}));

describe('Login Page Tests', () => {
    test('renders the Login page elements', () => {
        render(
            <MemoryRouter>
                <Login />
            </MemoryRouter>
        );

        // Check for heading
        expect(screen.getByText('Welcome back!')).toBeInTheDocument();

        // Check for email input
        expect(screen.getByPlaceholderText('username@thu.de')).toBeInTheDocument();

        // Check for password input
        expect(screen.getByPlaceholderText('.......')).toBeInTheDocument();

        // Check for "Forgot password" link
        expect(screen.getByText('Forgot password?')).toBeInTheDocument();

        // Check for login button
        expect(screen.getByRole('button', { name: /Log In/i })).toBeInTheDocument();
    });

    test('logs in the user when valid credentials are submitted', async () => {
        const mockedLogin = require('../api/auth').login;
        mockedLogin.mockResolvedValue({ success: true });

        render(
            <MemoryRouter>
                <Login />
            </MemoryRouter>
        );

        fireEvent.change(screen.getByPlaceholderText('username@thu.de'), { target: { value: 'testuser' } });
        fireEvent.change(screen.getByPlaceholderText('.......'), { target: { value: 'password123' } });
        fireEvent.click(screen.getByRole('button', { name: /Log In/i }));

        // Verify API call
        expect(mockedLogin).toHaveBeenCalledWith({ username: 'testuser', password: 'password123' });
    });
});
