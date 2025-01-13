import { render, screen, fireEvent } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import Signup from '../pages/Signup';

describe('Signup Page Tests', () => {
    test('renders the Signup page elements', () => {
        render(
            <MemoryRouter>
                <Signup />
            </MemoryRouter>
        );

        // Check for heading
        expect(screen.getByText('Come join us!')).toBeInTheDocument();

        // Check for first name input
        expect(screen.getByPlaceholderText('John')).toBeInTheDocument();

        // Check for last name input
        expect(screen.getByPlaceholderText('Doe')).toBeInTheDocument();

        // Check for email input
        expect(screen.getByPlaceholderText('username@thu.de')).toBeInTheDocument();

        // Check for password input
        expect(screen.getByPlaceholderText('Enter at least 8 characters')).toBeInTheDocument();

        // Check for role selection inputs
        expect(screen.getByLabelText('Student')).toBeInTheDocument();
        expect(screen.getByLabelText('Tutor')).toBeInTheDocument();
    });

    test('displays error when password is too short', () => {
        render(
            <MemoryRouter>
                <Signup />
            </MemoryRouter>
        );

        fireEvent.change(screen.getByPlaceholderText('Enter at least 8 characters'), { target: { value: 'short' } });
        fireEvent.click(screen.getByRole('button', { name: /Sign Up/i }));

        // Verify error message
        expect(screen.getByText(/Password must be at least 8 characters/i)).toBeInTheDocument();
    });

    test('submits the form when all fields are valid', () => {
        const handleSubmit = jest.fn();

        render(
            <MemoryRouter>
                <Signup onSubmit={handleSubmit} />
            </MemoryRouter>
        );

        fireEvent.change(screen.getByPlaceholderText('John'), { target: { value: 'John' } });
        fireEvent.change(screen.getByPlaceholderText('Doe'), { target: { value: 'Doe' } });
        fireEvent.change(screen.getByPlaceholderText('username@thu.de'), { target: { value: 'john.doe@example.com' } });
        fireEvent.change(screen.getByPlaceholderText('Enter at least 8 characters'), { target: { value: 'password123' } });
        fireEvent.click(screen.getByLabelText('Student'));
        fireEvent.click(screen.getByRole('button', { name: /Sign Up/i }));

        // Verify form submission
        expect(handleSubmit).toHaveBeenCalled();
    });
});
