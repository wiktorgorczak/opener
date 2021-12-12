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
        <Table data={Object.entries(report).map((e) => ({"key": e[0], "value": e[1]}))}>
            <Column width={400}>
                <HeaderCell>Key</HeaderCell>
                <Cell dataKey="key"/>
            </Column>
            <Column width={400}>
                <HeaderCell>Value</HeaderCell>
                <Cell dataKey="value"/>
            </Column>
        </Table>
        </>
    )
}

export default Report
