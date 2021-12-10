import './App.css';
import React from 'react';
import { ReactComponent as Icon} from './components/uploadIcon.svg';
import { Container, Header, Footer, Progress, Button } from 'rsuite'

function App() {

  return (
    <Container className="mainContainer" align="center">
      <Header className="mainHeader">
        <h2>Opener.gov</h2>
      </Header>
      <Container>
        <Icon className="uploadIcon"/>
      </Container>
      <Container>
        <input type="file"/>
        <br/>
        <Button>
          Upload!
        </Button>
      </Container>
      <Container>
        <Progress.Line />
      </Container>
        <Footer>AUUUUU</Footer>
    </Container>
  );
}

export default App;
