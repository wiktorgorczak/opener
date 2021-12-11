import React from "react";

const ProgressBar = (props) => {
  const { completed } = props;

  const containerStyles = {
    height: 10,
    width: '100%',
    backgroundColor: "#3d5a80",
    borderRadius: 50,
    marginBottom: '10%'
  }

  const fillerStyles = {
    height: '100%',
    width: `${completed}%`,
    backgroundColor: '#98c1d9',
    transition: 'width 1s ease-in-out',
    borderRadius: 'inherit',
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