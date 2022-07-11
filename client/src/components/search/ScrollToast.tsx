import { Chip } from '@mui/material'
import { Component } from 'react'

export interface ScrollToastState {
    open: boolean,
    timeout: NodeJS.Timeout | null,
    text: string,
}

export default class ScrollToast extends Component<{}, ScrollToastState> {
    state: ScrollToastState = {
        open: false,
        timeout: null,
        text: '',
    }

    render() {
        return (
            <>
                {this.state.open &&
                    <div
                        style={{
                            position: 'fixed',
                            width: '100%',
                            display: 'flex',
                            justifyContent: 'center',
                            marginTop: '10px'
                        }}>
                        <Chip
                            variant='filled'
                            color='info'
                            label={this.state.text}
                            style={{
                                width: '120px',
                                fontWeight: 'bold'
                            }} />
                    </div>
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