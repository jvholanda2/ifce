/*
 ************************************************************************************************
 *  File:           osa_stimers_old.c
 *
 *  Programmer:     Timofeev Victor
 ************************************************************************************************
 */



/************************************************************************************************
 *                                                                                              *
 *                           O L D   S T Y L E   S T A T I C   T I M E R S                      *
 *                                                                                              *
 ************************************************************************************************/

//******************************************************************************
//  Timeouts for old style static timers
//******************************************************************************

#if OS_TIMERS > 0

#if (OS_TIMEOUTS_BANK == 0) && defined(__OSA_PIC18_MPLABC__)
#pragma udata access os_bsems
#endif

    OS_TIMEOUTS_BANK OST_WORD OS_Timeouts[(OS_TIMERS + _OST_INT_SIZE - 1) / _OST_INT_SIZE] ;

#if (OS_TIMEOUTS_BANK == 0) && defined(__OSA_PIC18_MPLABC__)
#pragma udata
#endif

#endif



//******************************************************************************
//  32-bit old style static timers
//******************************************************************************

#if OS_TIMERS32 > 0

#if (OS_TIMERS32_BANK == 0) && defined(__OSA_PIC18_MPLABC__)
#pragma udata access os_bsems
#endif

OS_TIMERS32_BANK    OST_UINT32    OS_Timers32[OS_TIMERS32]    ;

#if (OS_TIMERS32_BANK == 0) && defined(__OSA_PIC18_MPLABC__)
#pragma udata
#endif
#endif


//******************************************************************************
//  24-bit old style static timers
//******************************************************************************

#if OS_TIMERS24 > 0

#if (OS_TIMERS24_BANK == 0) && defined(__OSA_PIC18_MPLABC__)
#pragma udata access os_bsems
#endif

OS_TIMERS24_BANK    OST_UINT16      OS_Timers24[OS_TIMERS24];
OS_BANK             OST_WORD        OS_Ticks;

#if (OS_TIMERS24_BANK == 0) && defined(__OSA_PIC18_MPLABC__)
#pragma udata
#endif
#endif


//******************************************************************************
//  16-bit old style static timers
//******************************************************************************

#if OS_TIMERS16 > 0

#if (OS_TIMERS16_BANK == 0) && defined(__OSA_PIC18_MPLABC__)
#pragma udata access os_bsems
#endif

OS_TIMERS16_BANK    OST_UINT16    OS_Timers16[OS_TIMERS16]    ;

#if (OS_TIMERS16_BANK == 0) && defined(__OSA_PIC18_MPLABC__)
#pragma udata
#endif

#endif


//******************************************************************************
//  8-bit old style static timers
//******************************************************************************

#if OS_TIMERS8 > 0

#if (OS_TIMERS8_BANK == 0) && defined(__OSA_PIC18_MPLABC__)
#pragma udata access os_bsems
#endif

OS_TIMERS8_BANK     OST_UINT8     OS_Timers8[OS_TIMERS8]      ;

#if (OS_TIMERS8_BANK == 0) && defined(__OSA_PIC18_MPLABC__)
#pragma udata
#endif
#endif



//------------------------------------------------------------------------------

//******************************************************************************
//  END OF FILE
//******************************************************************************

