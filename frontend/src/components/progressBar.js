import React from "react";

const ProgressBar = (props) => {
  const { completed } = props;

  const containerStyles = {
    height: 10,
    width: '50%',
    backgroundColor: "#3d5a80",
    borderRadius: 50,
    marginLeft: '25%',
    marginRight: '25%',
    marginTop: '2%'
  }

  const fillerStyles = {
    height: '100%',
    width: `${completed}%`,
    backgroundColor: '#98c1d9',
    transition: 'width 1s ease-in-out',
    borderRadius: 'inherit',
    textAlign: 'right',
  }

  return (
    <div style={containerStyles}>
      <div style={fillerStyles}>
          <center>
            <span></span>
        </center>
      </div>
    </div>
  );
};

export default ProgressBar;