import React, { useEffect, useReducer } from 'react'
import { sendDataToServer } from '../../hooks/sendData'
import { fetchDataFromServer } from '../../hooks/fetchData'
import './User.css'

const initialState = {
  tripDate: '',
  selectedReceipts: [],
  claimDays: '',
  disableSpecificDays: false,
  carDistance: '',
  fetchedData: [],
  receipts: [],
  distanceLimit: 0,
  receiptsLimit: 0,
  message: '',
  showError: false
}

function reducer(state, action) {
  switch (action.type) {
    case 'SET_TRIP_DATE':
      return { ...state, tripDate: action.payload }
    case 'ADD_RECEIPT':
      return { ...state, selectedReceipts: [...state.selectedReceipts, action.payload] }
    case 'SET_CLAIM_DAYS':
      return { ...state, claimDays: action.payload }
    case 'TOGGLE_DISABLE_DAYS':
      return { ...state, disableSpecificDays: !state.disableSpecificDays }
    case 'SET_CAR_DISTANCE':
      return { ...state, carDistance: action.payload }
    case 'SET_FETCHED_DATA':
      return { ...state, fetchedData: action.payload }
    case 'SET_RECEIPTS':
      return { ...state, receipts: action.payload }
    case 'SET_DISTANCE_LIMIT':
      return { ...state, distanceLimit: action.payload }
    case 'INITIAL_STATE':
      return {
        ...state, tripDate: initialState.tripDate,
        selectedReceipts: initialState.selectedReceipts,
        claimDays: initialState.claimDays,
        disableSpecificDays: initialState.disableSpecificDays,
        carDistance: initialState.carDistance
      }
    case 'SET_RECEIPTS_LIMIT':
      return { ...state, receiptsLimit: action.payload }
    case 'MESSAGE':
      return { ...state, message: action.payload.message, showError: action.payload.showError }
    default:
      return state
  }
}

const User = () => {
  const [state, dispatch] = useReducer(reducer, initialState)

  useEffect(() => {
    fetchAdminLimits()
    fetchReimbursements()
  }, [])

  const createReimbursement = async (event) => {
    event.preventDefault()
    const reimbursementJson = {
      tripDate: state.tripDate,
      selectedReceipts: state.selectedReceipts,
      claimDays: state.claimDays,
      disableSpecificDays: state.disableSpecificDays,
      carDistance: state.carDistance
    }

    try {
      const url = 'http://localhost:8080/setReimbursement'
      const responseData = await sendDataToServer(url, reimbursementJson)

      if (responseData === 'Data set successfully') {
        await fetchReimbursements()
        dispatch({ type: 'INITIAL_STATE' })
        dispatch({ type: 'MESSAGE', payload: { message: 'Reimbursement created successfully!', showError: false } })
      }
    } catch {
      dispatch({ type: 'MESSAGE', payload: { message: 'Error sending data to server!', showError: true } })
    }
  }

  const fetchAdminLimits = async () => {
    try {
      const url = 'http://localhost:8080/getAdminLimits'
      const responseString = await fetchDataFromServer(url)

      if (responseString) {
        const responseData = await JSON.parse(responseString)
        dispatch({ type: 'SET_RECEIPTS', payload: responseData.receipts })
        dispatch({ type: 'SET_DISTANCE_LIMIT', payload: responseData.distanceLimit })
        dispatch({ type: 'SET_RECEIPTS_LIMIT', payload: responseData.receiptsLimit })
        dispatch({ type: 'MESSAGE', payload: { message: 'Admin Limits fetched successfully!', showError: false } })
      }
    } catch (error) {
      dispatch({ type: 'MESSAGE', payload: { message: 'Error fetching data from server!', showError: true } })
    }
  }

  const fetchReimbursements = async () => {
    try {
      const responseString = await fetchDataFromServer('http://localhost:8080/getReimbursement')
      if (responseString) {
        const responseData = JSON.parse(responseString)

        if (Array.isArray(responseData)) {
          const updatedData = responseData.map(dataObject => {
            const reimbursementData = {
              tripDate: dataObject.tripDate,
              selectedReceipts: dataObject.selectedReceipts,
              claimDays: dataObject.claimDays,
              disableSpecificDays: dataObject.disableSpecificDays,
              carDistance: dataObject.carDistance,
              totalReimbursement: dataObject.totalReimbursement
            }
            return reimbursementData
          })

          dispatch({ type: 'SET_FETCHED_DATA', payload: updatedData })
          dispatch({ type: 'MESSAGE', payload: { errorMessage: 'Reimbursements fetched successfully!', showError: false } })
        }
      }
    } catch (error) {
      dispatch({ type: 'MESSAGE', payload: { errorMessage: 'Error fetching data from server!', showError: true } })
    }
  }

  const handleAddReceipt = (amount, name) => {
    if (name !== 'Select Receipt' && state.selectedReceipts.length < state.receiptsLimit) {
      dispatch({ type: 'ADD_RECEIPT', payload: { name: name, amount: amount } })
    }
  }

  const renderFetchedData = () => (
    <div className="reimbursements-container">
      <h1>Your Reimbursements:</h1>
      <div>
        {state.fetchedData.map((dataObject, index) => (
          <div key={index} className="reimbursement-entry">
            <h2>Reimbursement {index + 1}</h2>
            <hr />
            <p>Trip Date: {dataObject.tripDate}</p>
            <p>Selected Receipts:</p>
            <ul>
              {dataObject.selectedReceipts.map((receipt, i) => (
                <li key={i}>{receipt.name}</li>
              ))}
            </ul>
            <p>Claim Days: {dataObject.claimDays}</p>
            <p>Disable Specific Days: {dataObject.disableSpecificDays ? 'Yes' : 'No'}</p>
            <p>Car Distance (km): {dataObject.carDistance}</p>
            <hr />
            <h4>Total Reimbursement: ${dataObject.totalReimbursement.toFixed(2)}</h4>
          </div>
        ))}
      </div>
    </div>
  )

  return (
    <div className='user-view-container'>
      <div className='user-form'>
        <h1>Reimbursement Claim</h1>
        <hr />
        <form onClick={fetchAdminLimits} onSubmit={createReimbursement}>
          <div>
            <label>
              Trip Date:
              <input required
                type="date"
                value={state.tripDate}
                onChange={(e) => dispatch({ type: 'SET_TRIP_DATE', payload: e.target.value })}
              />
            </label>
          </div>

          <div>
            <label>
              Select Receipt:
              <select onChange={(e) => handleAddReceipt(parseFloat(e.target.value), e.target.options[e.target.selectedIndex].text)}>
                <option value="0">Select Receipt</option>
                {state.receipts.map((receipt, index) => (
                  <option key={index} value={receipt.amount}>{receipt.name + ": $" + receipt.amount}</option>
                ))}
              </select>
            </label>
          </div>

          <div>
            <label>
              Claim Days:
              <input required
                min="0"
                type="number"
                value={state.claimDays}
                onChange={(e) => dispatch({ type: 'SET_CLAIM_DAYS', payload: parseInt(e.target.value) })}
              />
            </label>
            <label>
              Disable Specific Days:
              <input
                type="checkbox"
                checked={state.disableSpecificDays}
                onChange={() => dispatch({ type: 'TOGGLE_DISABLE_DAYS' })}
              />
            </label>
          </div>

          <div>
            <label>
              Car Distance (km):
              <input required
                min="0"
                max={state.distanceLimit}
                type="number"
                value={state.carDistance}
                onChange={(e) => dispatch({ type: 'SET_CAR_DISTANCE', payload: parseInt(e.target.value) })}
              />
            </label>
          </div>

          <button type="submit">Create Reimbursement</button>
        </form>
        <hr />
        {state.showError ? <div className="error-message">{state.message}</div> : <div className="success-message">{state.message}</div>}
        <div>
          <label>Selected Receipts:</label>
          {state.selectedReceipts.map((receipt, index) => (
            <span key={index}>{receipt.name}{index !== state.selectedReceipts.length - 1 ? ', ' : ''}</span>
          ))}
        </div>

      </div>

      <div className='fetched-data'>
        {renderFetchedData()}
      </div>

    </div>
  )
}

export default User
