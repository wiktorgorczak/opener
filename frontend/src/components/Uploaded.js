import './Uploaded.css';
import React, { useEffect, useState } from 'react'
import { Container } from 'rsuite'
import { Cell, Column, HeaderCell, Table } from 'rsuite-table'
import JSONTree from 'react-json-tree'
import axios from 'axios';
import { useParams } from 'react-router';
// import { Cell, Column, HeaderCell } from 'rsuite-table';

const Uploaded = () => {
    const [tableData, setTableData] = useState([])
    const params = useParams()

    useEffect(() => {
        let xml2js = require('xml2js')
        let parser = new xml2js.Parser()
        axios.get('http://localhost:3111/api/file/download/' + params.id).then((res) => {
            console.log(res)
        })

        parser.parseString(`<Naglowek>
        <KodFormularza kodPodatku="PIT" kodSystemowy="PIT-11 (25)" rodzajZobowiazania="Z" wersjaSchemy="1-0E">PIT-11</KodFormularza>
        <WariantFormularza>25</WariantFormularza>
        <CelZlozenia poz="P_7">2</CelZlozenia>
        <Rok>2020</Rok>
        <KodUrzedu>1405</KodUrzedu>
        </Naglowek>`,
        function(err, res) {
        let globalId = 0

        const generateChildren = (label, obj) => {
            let newObj = {}
            newObj['id'] = globalId
            globalId++
            if(typeof obj == 'object') {
                newObj['children'] = []
                newObj['label'] = label
                newObj['type'] = 'object'
                newObj['itemCount'] = Object.entries(obj).length
    
                for(const [k,v] of Object.entries(obj)) {
                    newObj['children'] = newObj['children'].concat(generateChildren(k, v))
                } 
            } else if(Array.isArray(obj)) {
                newObj['children'] = []
                newObj['label'] = label
                newObj['type'] = 'array'
                newObj['itemCount'] = obj.length
    
                for(var i = 0; i < obj.length; i++) {
                    newObj['children'] = newObj['children'].concat(generateChildren("item no. " + i, obj[i]))
                }
            } else {
                newObj['label'] = label
                newObj['type'] = typeof obj
            }
    
            return newObj
        }

        setTableData([generateChildren('root', res)])
    })
    },[])
    console.log(tableData)
    return (
        <>
        <Container>

            <Table
            className="xmlTable"
            virtualized={true}
            isTree
            defaultExpandAllRows
            bordered
            cellBordered
            rowKey="id"
            data={tableData}
            /** shouldUpdateScroll: whether to update the scroll bar after data update **/
            shouldUpdateScroll={false}
            onExpandChange={(isOpen, rowData) => {
                console.log(isOpen, rowData);
            }}
    //   renderTreeToggle={(icon, rowData) => {
    //     if (rowData.children && rowData.children.length === 0) {
    //       return <Spinner spin />;
    //     }
    //     return icon;
    //   }}
    >
                <Column flexGrow={1}>
                    <HeaderCell>Label</HeaderCell>
                    <Cell dataKey="label"/>
                </Column>
                <Column width={100}>
                    <HeaderCell>Type</HeaderCell>
                    <Cell dataKey="type"/>
                </Column>
                <Column width={100}>
                    <HeaderCell>Item Count</HeaderCell>
                    <Cell dataKey="itemCount"/>
                </Column>
            </Table>
        </Container>
        </>
    )
}

export default Uploaded;
