import React, { useReducer } from 'react'
import './Login.css'
import Admin from '../Admin/Admin'
import User from '../User/User'

const initialState = {
  username: '',
  password: '',
  loggedIn: false,
  showError: false,
  role: ''
}

const reducer = (state, action) => {
  switch (action.type) {
    case 'SET_USERNAME':
      return { ...state, username: action.payload }
    case 'SET_PASSWORD':
      return { ...state, password: action.payload }
    case 'SET_ROLE':
      return { ...state, role: action.payload }
    case 'LOGIN':
      if (
        (state.username === 'admin' && state.password === 'admin') ||
        (state.username === 'user' && state.password === 'user')
      ) {
        return { ...state, loggedIn: true, showError: false }
      } else {
        return { ...state, showError: true }
      }
    default:
      return state
  }
}

const Login = () => {
  const [state, dispatch] = useReducer(reducer, initialState)

  const handleLogin = () => {
    dispatch({ type: 'LOGIN' })
    if (state.username.trim() === 'admin') {
      dispatch({ type: 'SET_ROLE', payload: 'admin' })
    } else if (state.username.trim() === 'user') {
      dispatch({ type: 'SET_ROLE', payload: 'user' })
    }
  }

  if (state.loggedIn) {
    if (state.role === 'admin') {
      return <Admin />
    } else if (state.role === 'user') {
      return <User />
    }
  }

  return (
    <div className="login-container">
      <div className="login-form">
        {state.showError && <div className="error-message">Invalid credentials. Please try again.</div>}
        <div>
          <label>Username:</label>
          <input
            data-testid="username"
            type="text"
            value={state.username}
            onChange={(e) => dispatch({ type: 'SET_USERNAME', payload: e.target.value.trim() })}
          />
        </div>
        <div>
          <label>Password:</label>
          <input
            data-testid="password"
            type="password"
            value={state.password}
            onChange={(e) => dispatch({ type: 'SET_PASSWORD', payload: e.target.value.trim() })}
          />
        </div>
        <button onClick={handleLogin}>Login</button>
      </div>
    </div>
  )
}

export default Login
