import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router'
import axios from 'axios';
import { Cell, Column, HeaderCell, Table } from 'rsuite-table';

const Report = (props) => {
    const params = useParams()
    const [report, setReport] = useState({})
    useEffect(() => {
        axios.get('http://localhost:8111/api/report/' + params.id)
        .then((result) => {
            setReport(result.data.payload)
        }).catch((e) => {
            alert(e)
        })
    }, [])
    
    console.log(report)
    return (
        <>
        {/*
        "id": 2,
        "name": "ttttest.pdf",
        "type": "application/pdf",
        "expectedSuffix": ".pdf",
        "realSuffix": ".pdf",
        "verified": false,
        "uploadDate": "2021-12-11T23:52:48Z",
        "size": 12204,
        "validationResult": null,
        "program": "Adobe Reader"
        */}
        {report.map((r) => (
        <>
        <tr>
            <td>ID</td>
            <td>{r.id}</td>
        </tr>
        </>
        ))}
            <h1>{params.id}</h1>
        </>
    )
}

export default Report
