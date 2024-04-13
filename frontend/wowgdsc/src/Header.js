import Container from 'react-bootstrap/Container';
import Navbar from 'react-bootstrap/Navbar';
import Button from 'react-bootstrap/Button';
import './Header.css'

function NavBar() {
  return (
    <Navbar expand="lg" className="bg-body-tertiary">
      <Container>
        <Navbar.Brand href="#home">Yeildify</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Button variant="primary" className="me-auto" href="#login">Login</Button>
          <Button variant="outline-primary" href="#signup">Signup</Button>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default NavBar;