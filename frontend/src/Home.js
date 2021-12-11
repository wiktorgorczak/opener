import './Home.css';
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { ReactComponent as Icon} from './components/uploadIcon.svg';
import { Container } from 'rsuite'
import ProgressBar from './components/progressBar'
import axios from 'axios'

const Home = () => {
    const [selectedFile, setSelectedFile] = useState(null);
    const [completed, setCompleted] = useState(0);
    const [uploadedIds, setUploadedIds] = useState([])


    const handleUpload = () => {
        console.log(selectedFile)
        const config = {
            onUploadProgress: progressEvent => setCompleted((progressEvent.loaded / progressEvent.total) *100),
            headers: {
                'content-type': 'multipart/form-data'
            }
        }
        const formData = new FormData()
        Array.from(selectedFile).forEach((f) => {formData.append("files", f)})
        axios.post('http://localhost:8111/api/file', formData, config).then((result) => {
            console.log(result)
            const uploaded = []
            result.data.payload.forEach((f) => { uploaded.push(f) })
            setUploadedIds(uploaded.map((f) =>  f.id ))
            setCompleted(true)
        }).catch((e) => {
            alert(e)
        })
    }

  return (
    <div className="homeContent">
        <Container>
            <Icon className="uploadIcon"/>
        </Container>
        <Container>
            <ProgressBar completed={completed} />
        </Container>
        <Container>
        <input
            className="inputFileContainer noselect"
            type="file"
            multiple
            onChange={(e) => {
                setSelectedFile(e.target.files)
                // setCompleted(100)
            }}
            />
        </Container>

        <Container>
            {
            (
                selectedFile &&
                    <input className="upload noselect" onClick={handleUpload} type="button" value="Upload!" />
                )
            }
        </Container>
    </div>
  );
}

export default Home;