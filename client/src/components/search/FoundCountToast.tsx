import { Snackbar } from '@mui/material'
import { Component } from 'react'

export interface FoundCountState {
    open: boolean,
    timeout: NodeJS.Timeout | null,
    text: string,
}

export default class FoundCountToast extends Component<{}, FoundCountState> {
    state: FoundCountState = {
        open: false,
        timeout: null,
        text: '',
    }

    render() {
        return (
            <>
                {this.state.open &&
                    <Snackbar
                        open={this.state.open}
                        message={this.state.text}
                    />
                }
            </>
        )
    }

    show(text: string) {
        if (this.state.timeout != null) {
            clearTimeout(this.state.timeout)
        }
        this.setState({
            open: true,
            text: text,
            timeout: setTimeout(() => this.setState({ open: false, }), 2000),
        })
    }

    hide() {
        if (this.state.timeout != null) {
            clearTimeout(this.state.timeout)
        }
        this.setState({
            open: false,
            timeout: null,
        })
    }
}