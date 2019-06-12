/*
 ************************************************************************************************

 *  File:           osa_squeue.c
 *
 *  Programmer:     Timofeev Victor
 ************************************************************************************************
 */


/************************************************************************************************
 *                                                                                              *
 *     M E S S A G E   Q U E U E S                                                              *
 *                                                                                              *
 ************************************************************************************************/


//------------------------------------------------------------------------------
#if defined(OS_ENABLE_SQUEUE) && !defined(OS_QUEUE_SQUEUE_IDENTICAL)
//------------------------------------------------------------------------------




/*
 ********************************************************************************
 *                                                                              *
 *  void _OS_Squeue_Send (OST_SQUEUE *pSQueue, OST_SMSG SMsg)                   *
 *                                                                              *
 *------------------------------------------------------------------------------*
 *                                                                              *
 *  description:    (Internal function called by system kernel througth         *
 *                  service _OS_SendSQueue)                                     *
 *                                                                              *
 *                  Add message into queue of simple messages. Delete first     *
 *                  message if there is no free room to add new message.        *
 *                  Service OS_SQueue_Send before adding new message checks for *
 *                  free room. Thus messages will not deleted accidentally.     *
 *                                                                              *
 *                                                                              *
 *  parameters:     pSQueue      - pointer to queue descriptor                  *
 *                  SMsg        - simple message to be added                    *
 *                                                                              *
 *  on return:      OS_IsEventError() return 1, if first message was pushed out *
 *                                                                              *
 *  Overloaded in:  "osa_pic12_htpicc.c"                                         *
 *                                                                              *
 ********************************************************************************
 */


//------------------------------------------------------------------------------
#if !defined(_OS_Squeue_Send_DEFINED)
//------------------------------------------------------------------------------

    void _OS_Squeue_Send (OST_SQUEUE * pSQueue, OST_SMSG SMsg)
    {
        OST_QUEUE_CONTROL   q;          // Temp variable to increase speed
        OST_UINT16 temp;              

        q = pSQueue->Q;
        _OS_Flags.bEventError = 0;                   // First we clear error flag


        //------------------------------------------------------
        // If there is no free room in queue, then replace
        // first message in queue by new SMsg

        if (q.cSize == q.cFilled)
        {
            pSQueue->pSMsg[q.cBegin] = SMsg;
            q.cBegin++;                             // Update pointer
            if (q.cBegin == q.cSize) q.cBegin = 0;

            _OS_Flags.bEventError = 1;               // Set error flag

            goto EXIT;                              // Skip adding
        }

        //------------------------------------------------------
        // There is a free room in queue.
        // Add new message at end of queue.

        temp = (OST_UINT16)q.cBegin + q.cFilled;  
        if (temp >= q.cSize) temp -= q.cSize;
        pSQueue->pSMsg[temp] = SMsg;
        q.cFilled++;                                // Update fillness

    EXIT:
        pSQueue->Q = q;

    }

//------------------------------------------------------------------------------
#endif  // !defined(_OS_Squeue_Send_DEFINED)
//------------------------------------------------------------------------------






/*
 ********************************************************************************
 *                                                                              *
 *  void _OS_Squeue_Send_I (OST_SQUEUE *pSQueue, OST_SMSG SMsg)                 *
 *                                                                              *
 *------------------------------------------------------------------------------*
 *                                                                              *
 *  description:    Copy of _OS_SQueue_Send to be called from interrupt         *
 *                                                                              *
 *  parameters:     pSQueue      - pointer to queue descriptor                  *
 *                  SMsg        - simple message to be added                    *
 *                                                                              *
 *  on return:      OS_IsEventError() return 1, if first message was pushed out *
 *                                                                              *
 *  Overloaded in:  -                                                           *
 *                                                                              *
 ********************************************************************************
 */


//------------------------------------------------------------------------------
#if defined(OS_ENABLE_INT_QUEUE) && !defined(_OS_Squeue_Send_I_DEFINED)
//------------------------------------------------------------------------------

    void _OS_Squeue_Send_I (OST_SQUEUE *pSQueue, OST_SMSG SMsg)
    {
        OST_QUEUE_CONTROL   q;
        OST_UINT16 temp;              

        q = pSQueue->Q;
        _OS_Flags.bEventError = 0;

        //------------------------------------------------------
        // If there is no free room in queue, then replace
        // first message in queue by new SMsg

        if (q.cSize == q.cFilled)
        {
            pSQueue->pSMsg[q.cBegin] = SMsg;
            q.cBegin++;
            if (q.cBegin == q.cSize) q.cBegin = 0;

            _OS_Flags.bEventError = 1;
            goto EXIT;
        }

        //------------------------------------------------------
        // There is a free room in queue.
        // Add new message at end of queue.

        temp = (OST_UINT16)q.cBegin + q.cFilled;  
        if (temp >= q.cSize) temp -= q.cSize;
        pSQueue->pSMsg[temp] = SMsg;
        q.cFilled++;

    EXIT:
        pSQueue->Q = q;
    }

//------------------------------------------------------------------------------
#endif  // (OS_ENABLE_INT_QUEUE) && !defined(_OS_Squeue_Send_I_DEFINED)
//------------------------------------------------------------------------------









/*
 ********************************************************************************
 *                                                                              *
 *  OST_SMSG _OS_Squeue_Get (OST_SQUEUE *pSQueue)                               *
 *                                                                              *
 *------------------------------------------------------------------------------*
 *                                                                              *
 *  description:    (Internal function called by system kernel througth         *
 *                  service OS_AcceptSQueue)                                    *
 *                                                                              *
 *                  Get first simple message from queue. Before calling this    *
 *                  function be sure that queue is not empty (OS_AcceptSQueue   *
 *                  does it automatically). After execution this function first *
 *                  message will be deleted from queue.                         *
 *                                                                              *
 *  parameters:     pSQueue      - pointer to queue descriptor                  *
 *                                                                              *
 *  on return:      first message from queue                                    *
 *                                                                              *
 *  Overloaded in:  "osa_pic12_htpicc.c"                                         *
 *                                                                              *
 ********************************************************************************
 */


//------------------------------------------------------------------------------
#if !defined(_OS_Squeue_Get_DEFINED)
//------------------------------------------------------------------------------

    OST_SMSG _OS_Squeue_Get (OST_SQUEUE *pSQueue)
    {
        OST_QUEUE_CONTROL   q;
        OST_UINT       temp;
        OST_SMSG            smsg_temp;

        q = pSQueue->Q;
        temp = q.cBegin;
        q.cBegin++;

        if (q.cBegin >= q.cSize)    q.cBegin = 0;

        q.cFilled--;
        pSQueue->Q = q;

        smsg_temp = pSQueue->pSMsg[temp];

        return  smsg_temp;
    }

//------------------------------------------------------------------------------
#endif  // !defined(_OS_Queue_Get_DEFINED)
//------------------------------------------------------------------------------




/*
 ********************************************************************************
 *                                                                              *
 *  OST_SMSG _OS_Squeue_Get_I (OST_SQUEUE *pSQueue)                             *
 *                                                                              *
 *------------------------------------------------------------------------------*
 *                                                                              *
 *  description:    (Internal function called by system kernel througth         *
 *                  service OS_Squeue_Accept_I)                                 *
 *                                                                              *
 *                  Get first simple message from queue. Before calling this    *
 *                  function be sure that queue is not empty (OS_AcceptSQueue   *
 *                  does it automatically). After execution this function first *
 *                  message will be deleted from queue.                         *
 *                                                                              *
 *  parameters:     pSQueue      - pointer to queue descriptor                  *
 *                                                                              *
 *  on return:      first message from queue                                    *
 *                                                                              *
 *  Overloaded in:  -                                                           *
 *                                                                              *
 ********************************************************************************
 */


//------------------------------------------------------------------------------
#if defined(OS_ENABLE_INT_QUEUE) && !defined(_OS_Squeue_Get_I_DEFINED)
//------------------------------------------------------------------------------

    OST_SMSG _OS_Squeue_Get_I (OST_SQUEUE *pSQueue)
    {
        OST_QUEUE_CONTROL   q;
        OST_UINT temp;

        q = pSQueue->Q;
        temp = q.cBegin;
        q.cBegin++;

        if (q.cBegin >= q.cSize)    q.cBegin = 0;

        q.cFilled--;
        pSQueue->Q = q;

        return  pSQueue->pSMsg[temp];
    }

//------------------------------------------------------------------------------
#endif  // defined(OS_ENABLE_INT_QUEUE) && !defined(_OS_Queue_Get_DEFINED)
//------------------------------------------------------------------------------


//------------------------------------------------------------------------------
#endif  // defined(OS_ENABLE_SQUEUE)
//------------------------------------------------------------------------------







