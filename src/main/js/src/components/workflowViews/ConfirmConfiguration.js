import React, { Component } from 'react'
import PropTypes from 'prop-types';
import _ from 'lodash'
import ConfigurationsView from './utilities/ConfigurationsView'
import {Link, withRouter } from 'react-router-dom';
import {Button} from 'react-bootstrap/'
import axios from 'axios';

class ConfirmConfiguration extends Component {


    constructor(...args) {
        super(...args)
        this.setData()

        this.state = {
            data : null
        } 

        this.setData()
    }

    componentDidMount = () => this.setData()
    componentDidUpdate = () => this.setData()

    setData = () => {

        axios.get("/workflow/get_configurations").then(res => {
            var data = res.data
            if (! _.isEqual(this.state.data, data)) 
                this.setState({data: data})     
        })
    }
   
    storeWorkflow = () => axios.get("/workflow/store").then(r => this.props.history.push("/workflow"))


    render() {
        window.scrollTo(0, 0)
        return (
            <div>
                <ConfigurationsView state={this.state.data} />
                <br/>
                <br/>
                
                <Button style={{float: 'right'}} onClick={this.storeWorkflow} >Confirm</Button>
                
                
            </div>
        )
    }
}



export default withRouter(ConfirmConfiguration)