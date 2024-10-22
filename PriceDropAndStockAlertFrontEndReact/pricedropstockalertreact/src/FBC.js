import React, { useState } from 'react'

export default function FBC(props) {
    let[state,changestate] = useState()//hook
  return (
    <div>
      <p>Iam FBC {props.topic} </p>
    </div>
  )
}
