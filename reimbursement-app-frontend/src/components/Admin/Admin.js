import React, { useReducer, useEffect } from 'react'
import { sendDataToServer } from '../../hooks/sendData'
import { fetchDataFromServer } from '../../hooks/fetchData'
import './Admin.css'

const initialState = {
  dailyAllowanceRate: 0,
  carMileageRate: 0,
  receipts: [],
  totalReimbursementLimit: 0,
  distanceLimit: 0,
  receiptsLimit: 0,
  message: '',
  showError: false
}

function reducer(state, action) {
  switch (action.type) {
    case 'SET_DAILY_ALLOWANCE_RATE':
      return { ...state, dailyAllowanceRate: action.payload }
    case 'SET_CAR_MILEAGE_RATE':
      return { ...state, carMileageRate: action.payload }
    case 'SET_DISTANCE_LIMIT':
      return { ...state, distanceLimit: action.payload }
    case 'SET_RECEIPTS':
      return { ...state, receipts: action.payload }
    case 'EDIT_RECEIPT':
      const updatedReceipts = state.receipts.map((receipt, index) =>
        index === action.payload.index
          ? { ...receipt, [action.payload.property]: action.payload.value }
          : receipt
      )
      return { ...state, receipts: updatedReceipts }
    case 'REMOVE_RECEIPT':
      const updatedReceiptsAfterRemove = state.receipts.filter((_, index) => index !== action.payload.index)
      return { ...state, receipts: updatedReceiptsAfterRemove }
    case 'ADD_RECEIPT':
      return { ...state, receipts: [...state.receipts, { name: '', amount: 0 }] }
    case 'SET_TOTAL_REIMBURSEMENT_LIMIT':
      return { ...state, totalReimbursementLimit: action.payload }
    case 'SET_RECEIPTS_LIMIT':
      return { ...state, receiptsLimit: action.payload }
    case 'MESSAGE':
      return { ...state, message: action.payload.message, showError: action.payload.showError }
    default:
      return state
  }
}

const Admin = () => {
  const [state, dispatch] = useReducer(reducer, initialState)

  const fetchAdminLimits = async () => {
    try {
      const url = 'http://localhost:8080/getAdminLimits'
      const responseString = await fetchDataFromServer(url)

      if (responseString) {
        const responseData = await JSON.parse(responseString)
        dispatch({ type: 'SET_DAILY_ALLOWANCE_RATE', payload: responseData.dailyAllowanceRate })
        dispatch({ type: 'SET_CAR_MILEAGE_RATE', payload: responseData.carMileageRate })
        dispatch({ type: 'SET_RECEIPTS', payload: responseData.receipts })
        dispatch({ type: 'SET_TOTAL_REIMBURSEMENT_LIMIT', payload: responseData.totalReimbursementLimit })
        dispatch({ type: 'SET_DISTANCE_LIMIT', payload: responseData.distanceLimit })
        dispatch({ type: 'SET_RECEIPTS_LIMIT', payload: responseData.receiptsLimit })
        dispatch({ type: 'MESSAGE', payload: { message: 'Admin Limits fetched successfully!', showError: false } })
      }
    } catch (error) {
      dispatch({ type: 'MESSAGE', payload: { message: 'Error fetching data from server!', showError: true } })
    }
  }

  const updateAdminLimits = async (event) => {
    event.preventDefault()
    const adminLimitsJson = {
      dailyAllowanceRate: state.dailyAllowanceRate,
      carMileageRate: state.carMileageRate,
      receipts: state.receipts,
      totalReimbursementLimit: state.totalReimbursementLimit,
      distanceLimit: state.distanceLimit,
      receiptsLimit: state.receiptsLimit
    }
    try {
      const url = 'http://localhost:8080/setAdminLimits'
      const responseData = await sendDataToServer(url, adminLimitsJson)

      if (responseData === 'Data set successfully') {
        dispatch({ type: 'MESSAGE', payload: { message: 'Admin Limits updated successfully!', showError: false } })
      }
    } catch (error) {
      dispatch({ type: 'MESSAGE', payload: { message: 'Error sending data to server!', showError: true } })
    }
  }

  useEffect(() => {
    fetchAdminLimits()
  }, [])

  const handleAddReceipt = () => {
    dispatch({ type: 'ADD_RECEIPT' })
  }

  const handleRemoveReceipt = (index) => {
    const updatedReceiptsAfterRemove = state.receipts.filter((_, i) => i !== index)
    dispatch({ type: 'SET_RECEIPTS', payload: updatedReceiptsAfterRemove })
  }

  return (
    <div className="admin-view-container">
      <div className='admin-form'>
        <form onSubmit={updateAdminLimits}>
          <h1>Admin Dashboard</h1>
          <hr />
          <div>
            <label>Daily Allowance Rate ($/day):</label>
            <input required
              type="number"
              value={state.dailyAllowanceRate}
              onChange={(e) => dispatch({ type: 'SET_DAILY_ALLOWANCE_RATE', payload: parseInt(e.target.value) })}
            />
          </div>
          <div>
            <label>Car Mileage Rate ($/km):</label>
            <input required
              type="number"
              value={state.carMileageRate}
              onChange={(e) => dispatch({ type: 'SET_CAR_MILEAGE_RATE', payload: parseFloat(e.target.value) })}
            />
          </div>

          <div>
            <label>Distance Limit (km):</label>
            <input required
              type="number"
              value={state.distanceLimit}
              onChange={(e) => dispatch({ type: 'SET_DISTANCE_LIMIT', payload: parseInt(e.target.value) })}
            />
          </div>

          <div>
            <label>Total Reimbursement Limit ($):</label>
            <input required
              type="number"
              value={state.totalReimbursementLimit}
              onChange={(e) => dispatch({ type: 'SET_TOTAL_REIMBURSEMENT_LIMIT', payload: parseFloat(e.target.value) })}
            />
          </div>

          <div>
            <label>Receipts Limit (amount):</label>
            <input required
              type="number"
              value={state.receiptsLimit}
              onChange={(e) => dispatch({ type: 'SET_RECEIPTS_LIMIT', payload: parseFloat(e.target.value) })}
            />
          </div>

          <div>
            <label>Edit Receipts:</label>
            <ul>
              {state.receipts.map((receipt, index) => (
                <li key={index}>
                  <input required
                    type="text"
                    value={receipt.name}
                    onChange={(e) =>
                      dispatch({
                        type: 'EDIT_RECEIPT',
                        payload: { index, value: e.target.value, property: 'name' }
                      })
                    }
                  />
                  <input required
                    type="number"
                    min="1"
                    value={receipt.amount}
                    onChange={(e) =>
                      dispatch({
                        type: 'EDIT_RECEIPT',
                        payload: { index, value: e.target.value, property: 'amount' }
                      })
                    }
                  />
                  <button type='button' onClick={() => handleRemoveReceipt(index)}>Remove</button>
                </li>
              ))}
            </ul>
          </div>
          <button type='button' onClick={handleAddReceipt}>Add Receipt</button>
          <button type='submit'>Update Reimbursement Limits</button>
          {state.showError ? <div className="error-message">{state.message}</div> : <div className="success-message">{state.message}</div>}
        </form>
      </div>
    </div>
  )
}

export default Admin
