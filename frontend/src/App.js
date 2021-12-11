import './App.css';
import React from 'react';
import { ReactComponent as Icon} from './components/uploadIcon.svg';
import { Container, Header, Footer, Button } from 'rsuite'
import ProgressBar from "@ramonak/react-progress-bar";

function App() {

  return (
    <Container className="mainContainer" align="center">
      <Header className="mainHeader noselect">
        Opener.gov
      </Header>
      <Container>
        <Icon className="uploadIcon"/>
      </Container>
      <Container className="inputFileContainer noselect">
        <input type="file"/>
      </Container>
      <Container>
        <Button classname="goNextButton noselect" size="medium" padding="50px">
          Upload!
        </Button>
      </Container>
      <Container className="progressBarContainer">
        <ProgressBar completed={33} customLabel=" " className="wrapper"
          barContainerClassName="container"
          completedClassName="barCompleted"
          labelClassName="label"/>
      </Container>
        <Footer classname="noselect">Copyright © 2021, All Right Reserved</Footer>
    </Container>
  );
}

export default App;
