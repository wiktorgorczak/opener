import './App.css';
import React, { useState } from 'react';
import { ReactComponent as Icon} from './components/uploadIcon.svg';
import { Container, Header, Footer, Table } from 'rsuite'
import ProgressBar from './components/progressBar'

const Home = () => {
    const [selectedFile, setSelectedFile] = useState(null);
  const [completed, setCompleted] = useState(33);
  const [uploaded, setUploaded] = useState(false);
  //const [isRaportReady, setRaportReady] = useState(false)

  return (
    <div className="home">
        <Container>
            <Icon className="uploadIcon"/>
        </Container>
        <Container>
        <input
            className="inputFileContainer noselect"
            type="file"
            multiple
            onChange={(e) => {
                setSelectedFile(e.target.files)
                setCompleted(66)
            }}
            />
            {
            selectedFile && <input className="upload noselect" type="button" value="Upload!"/> 
            }
        </Container>
        <Container>
            <ProgressBar completed={completed} />
        </Container>
    </div>
  );
}

export default Home;