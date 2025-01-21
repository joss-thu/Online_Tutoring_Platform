// src/__tests__/SelectField.test.js

import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import SelectField from '../components/SelectField';
// Removed the import for jest-dom as it's now set up globally

describe('SelectField Component', () => {
    const mockOnChange = jest.fn();

    const stringOptions = ['Option 1', 'Option 2', 'Option 3'];

    const categoryOptions = [
        { categoryName: 'Category A' },
        { categoryName: 'Category B' },
        { categoryName: 'Category C' },
    ];

    const addressOptions = [
        {
            streetName: 'Main St',
            houseNum: '123',
            campusName: 'North Campus',
            university: { universityName: 'Tech University' },
            city: 'Metropolis',
            postalCode: '12345',
            addressId: 'addr1',
        },
        {
            streetName: 'Second St',
            houseNum: '456',
            campusName: 'South Campus',
            university: { universityName: 'Science University' },
            city: 'Gotham',
            postalCode: '67890',
            addressId: 'addr2',
        },
    ];

    beforeEach(() => {
        jest.clearAllMocks();
    });

    test('renders correctly with string options', () => {
        render(
            <SelectField
                label="Select Option"
                id="select-string"
                name="stringSelect"
                value=""
                onChange={mockOnChange}
                options={stringOptions}
            />
        );

        // Check for the label
        expect(screen.getByLabelText('Select Option')).toBeInTheDocument();

        // Check for the disabled default option
        expect(screen.getByText('Select an option')).toBeInTheDocument();

        // Check for each string option
        stringOptions.forEach((option) => {
            expect(screen.getByRole('option', { name: option })).toBeInTheDocument();
        });
    });

    test('renders correctly with categoryName options', () => {
        render(
            <SelectField
                label="Select Category"
                id="select-category"
                name="categorySelect"
                value=""
                onChange={mockOnChange}
                options={categoryOptions}
            />
        );

        // Check for the label
        expect(screen.getByLabelText('Select Category')).toBeInTheDocument();

        // Check for the disabled default option
        expect(screen.getByText('Select an option')).toBeInTheDocument();

        // Check for each category option
        categoryOptions.forEach((option) => {
            expect(screen.getByRole('option', { name: option.categoryName })).toBeInTheDocument();
        });
    });

    test('renders correctly with address options', () => {
        render(
            <SelectField
                label="Select Address"
                id="select-address"
                name="addressSelect"
                value=""
                onChange={mockOnChange}
                options={addressOptions}
            />
        );

        // Check for the label
        expect(screen.getByLabelText('Select Address')).toBeInTheDocument();

        // Check for the disabled default option
        expect(screen.getByText('Select an option')).toBeInTheDocument();

        // Check for each formatted address option using regex
        addressOptions.forEach((option) => {
            const universityName = option.university.universityName || 'Unknown University';
            const formattedAddressRegex = new RegExp(
                `${universityName}\\s*•\\s*${option.houseNum},\\s*${option.streetName},\\s*${option.city},\\s*${option.postalCode}\\s*\\(${option.campusName}\\)`
            );
            expect(screen.getByRole('option', { name: formattedAddressRegex })).toBeInTheDocument();
        });
    });

    test('calls onChange handler with correct value when a string option is selected', () => {
        render(
            <SelectField
                label="Select Option"
                id="select-string"
                name="stringSelect"
                value=""
                onChange={mockOnChange}
                options={stringOptions}
            />
        );

        const selectElement = screen.getByLabelText('Select Option');
        fireEvent.change(selectElement, { target: { value: 'Option 2' } });

        expect(mockOnChange).toHaveBeenCalledTimes(1);
        expect(mockOnChange).toHaveBeenCalledWith(expect.any(Object)); // The event object
    });

    test('calls onChange handler with correct value when a category option is selected', () => {
        render(
            <SelectField
                label="Select Category"
                id="select-category"
                name="categorySelect"
                value=""
                onChange={mockOnChange}
                options={categoryOptions}
            />
        );

        const selectElement = screen.getByLabelText('Select Category');
        fireEvent.change(selectElement, { target: { value: 'Category B' } });

        expect(mockOnChange).toHaveBeenCalledTimes(1);
        expect(mockOnChange).toHaveBeenCalledWith(expect.any(Object)); // The event object
    });

    test('calls onChange handler with correct value when an address option is selected', () => {
        render(
            <SelectField
                label="Select Address"
                id="select-address"
                name="addressSelect"
                value=""
                onChange={mockOnChange}
                options={addressOptions}
            />
        );

        const selectElement = screen.getByLabelText('Select Address');
        fireEvent.change(selectElement, { target: { value: 'addr2' } });

        expect(mockOnChange).toHaveBeenCalledTimes(1);
        expect(mockOnChange).toHaveBeenCalledWith(expect.any(Object)); // The event object
    });

    test('displays the correct selected value for string options', () => {
        render(
            <SelectField
                label="Select Option"
                id="select-string"
                name="stringSelect"
                value="Option 3"
                onChange={mockOnChange}
                options={stringOptions}
            />
        );

        const selectElement = screen.getByLabelText('Select Option');
        expect(selectElement.value).toBe('Option 3');
    });

    test('displays the correct selected value for category options', () => {
        render(
            <SelectField
                label="Select Category"
                id="select-category"
                name="categorySelect"
                value="Category C"
                onChange={mockOnChange}
                options={categoryOptions}
            />
        );

        const selectElement = screen.getByLabelText('Select Category');
        expect(selectElement.value).toBe('Category C');
    });

    test('displays the correct selected value for address options', () => {
        const selectedAddressId = 'addr1';
        render(
            <SelectField
                label="Select Address"
                id="select-address"
                name="addressSelect"
                value={selectedAddressId}
                onChange={mockOnChange}
                options={addressOptions}
            />
        );

        const selectElement = screen.getByLabelText('Select Address');
        expect(selectElement.value).toBe(selectedAddressId);
    });

    test('does not render options with invalid format', () => {
        const invalidOptions = [
            { invalidKey: 'Invalid Option' },
            { name: 'Missing categoryName or streetName' },
            123, // Not an object or string
        ];

        render(
            <SelectField
                label="Select Invalid Options"
                id="select-invalid"
                name="invalidSelect"
                value=""
                onChange={mockOnChange}
                options={invalidOptions}
            />
        );

        // Only the disabled default option should be present
        expect(screen.getByText('Select an option')).toBeInTheDocument();

        // No other options should be rendered
        expect(screen.queryByRole('option', { name: 'Invalid Option' })).not.toBeInTheDocument();
        expect(screen.queryByRole('option', { name: 'Missing categoryName or streetName' })).not.toBeInTheDocument();
        expect(screen.queryByRole('option', { name: '123' })).not.toBeInTheDocument();
    });
});
