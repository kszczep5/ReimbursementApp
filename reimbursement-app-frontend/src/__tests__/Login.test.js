import React from 'react'
import { render, screen, fireEvent } from '@testing-library/react'
import Login from '../components/Login/Login'

jest.mock('../components/Admin/Admin', () => () => <div>Admin Dashboard</div>)
jest.mock('../components/User/User', () => () => <div>User Dashboard</div>)

describe('Login Component', () => {
  it('Shows error message on invalid credentials', () => {
    render(<Login />)
    
    const usernameInput = screen.getByTestId('username')
    const passwordInput = screen.getByTestId('password')
    const loginButton = screen.getByText('Login')
    
    fireEvent.change(usernameInput, { target: { value: 'invalid' } })
    fireEvent.change(passwordInput, { target: { value: 'credentials' } })
    fireEvent.click(loginButton)
    
    const errorMessage = screen.getByText('Invalid credentials. Please try again.')
    expect(errorMessage).toBeInTheDocument()
  })

  it('Navigates to Admin component after successful admin login', () => {
    render(<Login />)
    
    const usernameInput = screen.getByTestId('username')
    const passwordInput = screen.getByTestId('password')
    const loginButton = screen.getByText('Login')
    
    fireEvent.change(usernameInput, { target: { value: 'admin' } })
    fireEvent.change(passwordInput, { target: { value: 'admin' } })
    fireEvent.click(loginButton)
    
    const adminDashboard = screen.getByText('Admin Dashboard')
    expect(adminDashboard).toBeInTheDocument()
  })

  it('Navigates to User component after successful user login', () => {
    render(<Login />)
    
    const usernameInput = screen.getByTestId('username')
    const passwordInput = screen.getByTestId('password')
    const loginButton = screen.getByText('Login')
    
    fireEvent.change(usernameInput, { target: { value: 'user' } })
    fireEvent.change(passwordInput, { target: { value: 'user' } })
    fireEvent.click(loginButton)
    
    const userDashboard = screen.getByText('User Dashboard')
    expect(userDashboard).toBeInTheDocument()
  })
})
