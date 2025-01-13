import { render, screen, fireEvent } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import Home from '../pages/Home';

// Mock navigate function
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => jest.fn(),
}));

describe('Home Page Tests', () => {
    test('renders the Home page elements', () => {
        render(
            <MemoryRouter>
                <Home />
            </MemoryRouter>
        );

        // Check for heading
        expect(screen.getByText('Find your perfect tutor')).toBeInTheDocument();

        // Check for the search input
        expect(screen.getByPlaceholderText('Search for')).toBeInTheDocument();

        // Check for the "See All" button
        expect(screen.getByRole('button', { name: /See All/i })).toBeInTheDocument();
    });

    test('navigates to tutor list when "See All" button is clicked', () => {
        const mockedNavigate = jest.fn();

        render(
            <MemoryRouter>
                <Home />
            </MemoryRouter>
        );

        const seeAllButton = screen.getByRole('button', { name: /See All/i });
        fireEvent.click(seeAllButton);

        // Verify navigation was triggered
        expect(mockedNavigate).toHaveBeenCalledWith('/courses');
    });
});
