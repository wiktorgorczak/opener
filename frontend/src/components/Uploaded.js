import React, { useEffect, useState } from 'react'
import { Cell, Column, HeaderCell, Table } from 'rsuite-table'
// import 'xml2js'

const Uploaded = (params) => {

    const [tableData, setTableData] = useState([])
    useEffect(() => {
        let xml2js = require('xml2js')
        let parser = new xml2js.Parser()
    //     parser.parseString(`<Naglowek>
    //     <KodFormularza kodPodatku="PIT" kodSystemowy="PIT-11 (25)" rodzajZobowiazania="Z" wersjaSchemy="1-0E">PIT-11</KodFormularza>
    //     <WariantFormularza>25</WariantFormularza>
    //     <CelZlozenia poz="P_7">2</CelZlozenia>
    //     <Rok>2020</Rok>
    //     <KodUrzedu>1405</KodUrzedu>
    // </Naglowek>`, function(err, res) {
    //     console.log(res)
    //     setTableData([res])
    // })
    setTableData([
        {
            "id": 1,
            "imie": "Jan",
            "nazwisko": "Kowalski"
        },
        {
            "id": 2,
            "imie": "Jan",
            "nazwisko": "Nowak"
        },
        {
            "id": 3,
            "imie": "Anna",
        "nazwisko": "Nowak"
    }
    ])
    },[])
    

    return (
        <>
            <Table 
                // isTree
                // defaultExpandAllRows
                // bordered
                // cellBordered
                rowKey="id"
                data={tableData}>
            </Table>
            <Column>
                <HeaderCell>
                    Imie
                </HeaderCell>
                <Cell dataKey="imie"/>

            </Column>
        </>
    )
}

export default Uploaded;
