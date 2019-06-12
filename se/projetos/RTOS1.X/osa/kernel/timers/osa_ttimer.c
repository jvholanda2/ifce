/*
 ************************************************************************************************
 *  File:           osa_ttimer.c
 *
 *  Programmer:     Timofeev Victor
 ************************************************************************************************
 */

//------------------------------------------------------------------------------
#ifdef OS_ENABLE_TTIMERS
//------------------------------------------------------------------------------



/*
 ********************************************************************************
 *
 *   void _OS_InitDelay (OS_TTIMER_TYPE Delay)
 *
 *------------------------------------------------------------------------------
 *
 *  description:    (Internal function called by system kernel)
 *
 *                  Init task timer delay.Set timer variable in task descriptor,
 *                  sets bit bDelay and clears bit bCanContinue.
 *
 *                  _OS_CurTask must point to descriptor.
 *
 *
 *  parameters:     Delay   - time of delay in system ticks
 *
 *  on return:      none
 *
 *
 *  Overloaded in:  "osa_pic12_htpicc.c"
 *                  "osa_pic16_htpicc.c"
 *                  "osa_pic16_ccs.c"
 *                  "osa_pic16_mikroc.c"
 *                  "osa_pic18_htpicc.c"
 *                  "osa_pic18_mplabc.c"
 *                  "osa_pic24_mplabc.c"
 *                  "osa_stm8_cosmic.c"
 *
 ********************************************************************************
 */




//------------------------------------------------------------------------------
#if !defined(_OS_InitDelay_DEFINED)
//------------------------------------------------------------------------------

    void _OS_InitDelay (OS_TTIMER_TYPE Delay)
    {

        _OS_TASK_ATOMIC_WRITE_A(
            _OS_bTaskCanContinue = 0;
            _OS_CurTask->State.bDelay = 0;
        );

        if (Delay)
        {
            Delay ^= (OS_TTIMER_TYPE)-1;
            Delay ++;
            _OS_CurTask->Timer = Delay;
            _OS_TASK_ATOMIC_WRITE_A(
                _OS_CurTask->State.bReady = 1;
                _OS_CurTask->State.bDelay = 1;
            );
            #if defined(_OS_CUR_FLAGS_IN_OS_STATE)
                _OS_bTaskDelay = 1;
                _OS_bTaskReady = 1;
            #endif
        }
    }

//------------------------------------------------------------------------------
#endif  // !defined(_OS_InitDelay_DEFINED)
//------------------------------------------------------------------------------




//------------------------------------------------------------------------------
#endif      // OS_ENABLE_TTIMERS
//------------------------------------------------------------------------------

//******************************************************************************
//  END OF FILE
//******************************************************************************

