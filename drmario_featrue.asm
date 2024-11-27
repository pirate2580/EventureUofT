################# CSC258 Assembly Final Project ###################
# This file contains our implementation of Dr Mario.
#
# Student 1: Serena Chen, 1009887954
# Student 2: Edric Ho, 1009939444
#
# We assert that the code submitted here is entirely our own 
# creation, and will indicate otherwise when it is not.
#
######################## Bitmap Display Configuration ########################
# - Unit width in pixels:       4
# - Unit height in pixels:      4
# - Display width in pixels:    256
# - Display height in pixels:   256
# - Base Address for Display:   0x10008000 ($gp)
######################### Other Unit Explainations ###########################
# When measuring in normal unit:
# Operable Area inside bottle went from (5, 7) to (36, 58) inclusively. 
# Virus Area went from (5, 19) to (36, 58) inclusively. 

# When measuring in virus unit:
# Board: 8-v_unit rows, 13-v_unit cols.     (8*4 = 32) (13*4 = 52)
# V_Area: 8-v_unit rows, 10-v_unit cols.    (8*4 = 32) (10*4 = 40)
######################### Global Variables Explanations #####################
# $s0 holds the base display address
# $s1 one of the $ra, a special location (can be used and replaced in other units)/ also gravity counts
# $s3 is the X coordinate of the previous capsule
# $s4 is the Y coordinate of the previous capsule
# $s5 is whether the previous capsule was horizontal (1) or vertical (2)
# $s7 is the random color assigned
#############################################################################

    .data
##############################################################################
# Immutable Data
##############################################################################
# The address of the bitmap display. Don't forget to connect it!
ADDR_DSPL:
    .word 0x10008000
# The address of the keyboard. Don't forget to connect it!
ADDR_KBRD:
    .word 0xffff0000
COLOUR_RED:      
    .word 0x00ff0000    
COLOUR_GREEN:
    .word 0x0000ff00    
COLOUR_BLUE:  
    .word 0x000000ff    
COLOUR_YELLOW: 
    .word 0x00ffff00   
COLOUR_GREY:  
    .word 0x00808080   
COLOUR_BLACK:     
    .word 0x00000000  
COLOUR_WHITE:
    .word 0x00ffffff
# Call gravity every 200 iterations
CURRENT_GRAVITY_THRESHOLD: 
    .word 1000000        
PREP_CAPSULE_X:
    .word 48
PREP_CAPSULE_Y:
    .word 20
INIT_CAPSULE_X:
    .word 17
INIT_CAPSULE_Y:
    .word 3
# Game Mode Threshold
GRAVITY_THRESHOLD_HIGH:
    .word 2000   # Higher speed (faster drop)
GRAVITY_THRESHOLD_MEDIUM:
    .word 1000000  # Medium speed (default)
GRAVITY_THRESHOLD_LOW:
    .word 3000000  # Lower speed (slower drop)
MSG_MODE_CHANGED:
    .asciiz "Game mode changed.\n"
# Game Pause Data
IS_PAUSED:
    .word 0
last_gravity_count:
    .word 0
##############################################################################
# Mutable Data
##############################################################################
NUM_VIRUS:
    .word 3
# Initialize the color of the capsule to be grey before randomly chose a color
CAPSULE_COLOR_1:            
    .word 0x00808080   
CAPSULE_COLOR_2:
    .word 0x00808080   
    
# Initialize the prep color of the capsule to be grey before randomly chose a color
CAPSULE_PREP_COLOR_1:            
    .word 0x00808080   
CAPSULE_PREP_COLOR_2:
    .word 0x00808080   
##############################################################################
# Code
##############################################################################
	.text
	.globl main


    # Run the game.
main:
    # Initialize the game
        
    # Load base address for display
    lw $s0, ADDR_DSPL                           # $s0 holds the base display address
    
    # jal game_config                             # Jump to game_settings
    lw $s1, CURRENT_GRAVITY_THRESHOLD                 # Set up gravity counter: only 100 game_loop let you add gravity
    jal draw_screen                             # Draw the screen
    j game_loop                                 # Jump to game_loop
    
    # j tests                                   # tests for testing whether some components are working as intended


game_loop:
    jal key_pressed                             # Check if key has been pressed 
    lw $t0, IS_PAUSED                          # Load pause state
    bnez $t0, game_loop_sleep                  # If paused, skip updates
    jal add_gravity                            # Add gravity per GRAVITY_THRESHOLD loops, also check whether reached bottom
    
	# 4. Sleep
    li 		$v0, 32                             # Instruction for sleep
	li 		$a0, 16                             # Sleeps for 16ms
	syscall

    # 5. Go back to Step 1
    j game_loop 


add_gravity:                                    # Both add gravity and check whether the capsule is at bottom
    addi $s1, $s1, -1                           # In each game loop, count as one time
    bnez $s1, game_loop                         # If s1 not GRAVITY_THRESHOLD loops yet, keep looping, otherwise,
    
    addi $sp, $sp, -4                           # Create space in $sp
    sw $ra, 0($sp)                              # Store $ra
    
    jal gravity                                 # Add on gravity
    jal check_bottom                            # Check whether the capsule is at the bottom
    lw $s1, CURRENT_GRAVITY_THRESHOLD                   # Reset gravity counter
    
    lw $ra, 0($sp)                              # Retrieve $ra
    addi $sp, $sp, 4                            # Release space from sp
    jr $ra                                      # Jump back to game loop


##############################################################################################################################################################################################
draw_screen:
    addi $sp, $sp, -4                           # Create space in $sp
    sw $ra, 0($sp)                              # Store $ra
    jal draw_bottle
    
    lw $t0, PREP_CAPSULE_X                      # X-unit coordinate on top left corner
    lw $t1, PREP_CAPSULE_Y                      # Y-unit coordinate on top left corner
    jal draw_first_capsule_prep                 # Draw the prep capsule next to the bottle
    
    lw $t0, INIT_CAPSULE_X                      # Capsule X-unit coordinate on top left corner
    lw $t1, INIT_CAPSULE_Y                      # Capsule Y-unit coordinate on top left corner
    jal draw_first_capsule_init                 # Draw the initial capsule above the bottle
    
    jal draw_virus                              # Draw NUM_VIRUS number of virus in the bottle
    
    lw $ra, 0($sp)                              # Retrieve $ra
    addi $sp, $sp, 4                            # Release space from sp
    jr $ra                                      # Jump back to game_loop 3.


##############################################################################################################################################################################################
random_color:                                   # **: OUTPUT s7: RAN_COLOR, For NUM_VIRUS = 3
    lw $t0, COLOUR_RED
    lw $t1, COLOUR_GREEN
    lw $t2, COLOUR_BLUE

    li $v0, 42                                  # Instruction for random number generator
    li $a0, 0                                   # Pick random number from 0
    li $a1, 3                                   # To 3 (0, 1, 2)
    syscall
        
    # Deal with different cases
    beq $a0, 0, select_0
    beq $a0, 1, select_1
    beq $a0, 2, select_2
    jr $ra             

select_0:
    move $s7, $t0
    jr $ra
select_1:
    move $s7, $t1
    jr $ra
select_2:
    move $s7, $t2
    jr $ra


##############################################################################################################################################################################################
check_collision_horizontal:
    beq $s5, 1, check_collision_horizontal_ch     # For capsules being horizontal
    beq $s5, 2, check_collision_horizontal_cv     # For capsules being vertical
    

check_collision_vertical:
    beq $s5, 1, check_collision_vertical_ch     # For capsules being horizontal
    beq $s5, 2, check_collision_vertical_cv     # For capsules being vertical


check_collision_rotation:
    beq $s5, 1, check_collision_rotation_ch     # For capsules being horizontal
    beq $s5, 2, check_collision_rotation_cv     # For capsules being vertical
    
    
check_bottom:
    beq $s5, 1, check_bottom_ch                 # For capsules being horizontal
    beq $s5, 2, check_bottom_cv                 # For capsules being vertical
    

check_collision_vertical_ch:                       # Given $t7 = intended vertical change location, Capsule horizontal, update the next location
    move $t0, $s3                               # Assign t0 with s3's value
    move $t1, $s4                               # Assign t1 with s4's value
    
    add $t6, $t7, $t1                           # Store vertical intended destination coordinate y to $t6
    # When there are things below on the left v_unit
    mul $t4, $t6, 64                        # Calculate Y offset: Y * 64
    add $t4, $t4, $t0                       # Add X offset
    sll $t4, $t4, 2                         # Multiply by 4
    add $t5, $s0, $t4                       # Calculate address in display memory
    lw $t9, 0($t5)                              # Load things from $t5 to $t9
    bne $t9, $zero, forbidden               # Check whether intended destination has things, yes, jump to forbidden
    
    addi $t3, $t0, 4
    # When there are things below on the right v_unit
    mul $t4, $t6, 64                        # Calculate Y offset: Y * 64
    add $t4, $t4, $t3                       # Add X offset
    sll $t4, $t4, 2                         # Multiply by 4
    add $t5, $s0, $t4                       # Calculate address in display memory
    lw $t9, 0($t5)                              # Load things from $t5 to $t9
    bne $t9, $zero, forbidden               # Check whether intended destination has things, yes, jump to forbidden
    
    # bge $t6, 59, forbidden                       # If your intended destination is greater than or equal to bottle bottom edge (you can only go down), jump to forbidden, Otherwise,
    move $t1, $t6                               # Update your y coordinate to intended destination
    jr $ra                                      # Jump back to caller
    
    
check_collision_vertical_cv:                       # Given $t7 = intended vertical change location, Capsule vertical, update the next location
    move $t0, $s3                               # Assign t0 with s3's value
    move $t1, $s4                               # Assign t1 with s4's value
    
    add $t6, $t7, $t1                           # Store vertical intended destination coordinate y to $t6
     # When there are things below
    addi $t8, $t6, 4                        # Consider the pixel below the lower part of the capsule
    mul $t4, $t8, 64                        # Calculate Y offset: Y * 64
    add $t4, $t4, $t0                       # Add X offset
    sll $t4, $t4, 2                         # Multiply by 4
    add $t5, $s0, $t4                       # Calculate address in display memory
    lw $t9, 0($t5)                              # Load things from $t5 to $t9
    bne $t9, $zero, forbidden               # Check whether intended destination has things, yes, jump to forbidden
    
    # bge $t6, 55, forbidden                       # If your intended destination is greater than or equal to bottle bottom edge (you can only go down), jump to forbidden, Otherwise,
    move $t1, $t6                               # Update your y coordinate to intended destination
    jr $ra                                      # Jump back to caller


check_collision_horizontal_ch:                    # Given $t7 = intended horizontal change location, Capsule horizontal, update the next location
    move $t0, $s3                               # Assign t0 with s3's value
    move $t1, $s4                               # Assign t1 with s4's value
    
    add $t6, $t7, $t0                           # Store horizontal intended destination coordinate x to $t6

    # When there are things on the left
    addi $t8, $t6, 3                        # Calculate pixel on the very next left
    mul $t4, $t1, 64                        # Calculate Y offset: Y * 64
    add $t4, $t4, $t8                       # Add X offset
    sll $t4, $t4, 2                         # Multiply by 4
    add $t5, $s0, $t4                       # Calculate address in display memory
    lw $t9, 0($t5)                              # Load things from $t5 to $t9
    bne $t9, $zero, forbidden               # Check whether intended destination has things, yes, jump to forbidden
    
    # ble $t6, 4, forbidden                       # If your intended destination is less than or equal to left bottle edge, jump to forbidden
    
    # When there are things on the right
    addi $t8, $t6, 4                        # Calculate pixel on the right
    mul $t4, $t1, 64                        # Calculate Y offset: Y * 64
    add $t4, $t4, $t8                       # Add X offset
    sll $t4, $t4, 2                         # Multiply by 4
    add $t5, $s0, $t4                       # Calculate address in display memory
    lw $t9, 0($t5)                              # Load things from $t5 to $t9
    bne $t9, $zero, forbidden               # Check whether intended destination has things, yes, jump to forbidden
    
    # bge $t6, 33, forbidden                       # If your intended destination is greater than or equal to right bottle edge, jump to forbidden, Otherwise,
    move $t0, $t6                               # Update your x coordinate to intended destination
    jr $ra                                      # Jump back to caller
    

check_collision_horizontal_cv:                    # Given $t7 = intended horizontal change location, Capsule vertical, update the next location
    move $t0, $s3                               # Assign t0 with s3's value
    move $t1, $s4                               # Assign t1 with s4's value
    
    add $t6, $t7, $t0                           # Store horizontal intended destination coordinate x to $t6
    
    # When there are things on the first pixel left
    addi $t3, $t6, 3                        # Calculate pixel on the very next left
    mul $t4, $t1, 64                        # Calculate Y offset: Y * 64
    add $t4, $t4, $t3                       # Add X offset
    sll $t4, $t4, 2                         # Multiply by 4
    add $t5, $s0, $t4                       # Calculate address in display memory
    lw $t9, 0($t5)                              # Load things from $t5 to $t9
    bne $t9, $zero, forbidden               # Check whether intended destination has things, yes, jump to forbidden
    # ble $t6, 4, forbidden                       # If your intended destination is less than or equal to left bottle edge, jump to forbidden
    
    # When there are things on the second pixel left
    addi $t8, $t1, 4                        # Calculate pixel on the very next left
    mul $t4, $t8, 64                        # Calculate Y offset: Y * 64
    add $t4, $t4, $t3                       # Add X offset
    sll $t4, $t4, 2                         # Multiply by 4
    add $t5, $s0, $t4                       # Calculate address in display memory
    lw $t9, 0($t5)                              # Load things from $t5 to $t9
    bne $t9, $zero, forbidden               # Check whether intended destination has things, yes, jump to forbidden
    
    # When there are things on the first pixel right
    mul $t4, $t1, 64                        # Calculate Y offset: Y * 64
    add $t4, $t4, $t6                       # Add X offset
    sll $t4, $t4, 2                         # Multiply by 4
    add $t5, $s0, $t4                       # Calculate address in display memory
    lw $t9, 0($t5)                              # Load things from $t5 to $t9
    bne $t9, $zero, forbidden               # Check whether intended destination has things, yes, jump to forbidden
    # bge $t6, 37, forbidden                       # If your intended destination is greater than or equal to right bottle edge, jump to forbidden, Otherwise,
    
    move $t0, $t6                               # Update your x coordinate to intended destination
    jr $ra                                      # Jump back to caller


check_collision_rotation_ch:
    move $t0, $s3                               # Assign t0 with s3's value
    move $t1, $s4                               # Assign t1 with s4's value
    
    addi $t6, $t1, 4                           # Store rotated intended destination coordinate y to $t6
    
    mul $t4, $t6, 64                        # Calculate Y offset: Y * 64
    add $t4, $t4, $t0                       # Add X offset
    sll $t4, $t4, 2                         # Multiply by 4
    add $t5, $s0, $t4                       # Calculate address in display memory
    lw $t9, 0($t5)                              # Load things from $t5 to $t9
    bne $t9, $zero, forbidden               # Check whether intended destination has things, yes, jump to forbidden
    
    bge $t6, 59, forbidden                       # If your intended destination is greater than or equal to bottle bottom edge (you can only go down), jump to forbidden, Otherwise,
    li $s5, 2                                   # Change your capsule from horizontal to vertical
    jr $ra                                      # Jump back to caller


check_collision_rotation_cv:
    move $t0, $s3                               # Assign t0 with s3's value
    move $t1, $s4                               # Assign t1 with s4's value
    
    add $t6, $t0, 4                           # Store rotated intended destination coordinate x to $t6
    
    mul $t4, $t1, 64                        # Calculate Y offset: Y * 64
    add $t4, $t4, $t6                       # Add X offset
    sll $t4, $t4, 2                         # Multiply by 4
    add $t5, $s0, $t4                       # Calculate address in display memory
    lw $t9, 0($t5)                              # Load things from $t5 to $t9
    bne $t9, $zero, forbidden               # Check whether intended destination has things, yes, jump to forbidden
    
    bge $t6, 37, forbidden                       # If your intended destination is greater than or equal to right bottle edge, jump to forbidden, Otherwise,
    li $s5, 1                                  # Change your capsule from vertical to horizontal
    jr $ra                                      # Jump back to caller
    
    
check_bottom_cv:
    move $t0, $s3                               # Assign t0 with s3's value
    move $t1, $s4                               # Assign t1 with s4's value
    
    add $t6, $t1, 8                           # Store intended destination coordinate y to $t6
    
    mul $t4, $t6, 64                        # Calculate Y offset: Y * 64
    add $t4, $t4, $t0                       # Add X offset
    sll $t4, $t4, 2                         # Multiply by 4
    add $t5, $s0, $t4                       # Calculate address in display memory
    lw $t9, 0($t5)                              # Load things from $t5 to $t9
    bne $t9, $zero, add_prep_capsule               # Check whether intended destination has things, yes, jump to add prep capsule, otherwise,
    jr $ra                                      # Jump back to caller


check_bottom_ch:
    move $t0, $s3                               # Assign t0 with s3's value
    move $t1, $s4                               # Assign t1 with s4's value
    
    add $t6, $t1, 4                           # Store intended destination coordinate y to $t6
    
    # When there are things below on the left v_unit
    mul $t4, $t6, 64                        # Calculate Y offset: Y * 64
    add $t4, $t4, $t0                       # Add X offset
    sll $t4, $t4, 2                         # Multiply by 4
    add $t5, $s0, $t4                       # Calculate address in display memory
    lw $t9, 0($t5)                              # Load things from $t5 to $t9
    bne $t9, $zero, add_prep_capsule               # Check whether intended destination has things, yes, jump to add prep capsule
    
    addi $t3, $t0, 4
    # When there are things below on the right v_unit
    mul $t4, $t6, 64                        # Calculate Y offset: Y * 64
    add $t4, $t4, $t3                       # Add X offset
    sll $t4, $t4, 2                         # Multiply by 4
    add $t5, $s0, $t4                       # Calculate address in display memory
    lw $t9, 0($t5)                              # Load things from $t5 to $t9
    bne $t9, $zero, add_prep_capsule               # Check whether intended destination has things, yes, jump to add prep capsule, otherwise,
    
    jr $ra                                      # Jump back to caller
    

check_bottle_neck:
    lw $t0, INIT_CAPSULE_X                              # Assign t0 with s3's value
    lw $t1, INIT_CAPSULE_Y                              # Assign t1 with s4's value
    
    add $t6, $t1, 4                           # Store intended destination coordinate y to $t6
    
    # When there are things below on the left v_unit
    mul $t4, $t6, 64                        # Calculate Y offset: Y * 64
    add $t4, $t4, $t0                       # Add X offset
    sll $t4, $t4, 2                         # Multiply by 4
    add $t5, $s0, $t4                       # Calculate address in display memory
    lw $t9, 0($t5)                              # Load things from $t5 to $t9
    bne $t9, $zero, exit_game               # Check whether intended destination has things, yes, end game
    
    addi $t3, $t0, 4
    # When there are things below on the right v_unit
    mul $t4, $t6, 64                        # Calculate Y offset: Y * 64
    add $t4, $t4, $t3                       # Add X offset
    sll $t4, $t4, 2                         # Multiply by 4
    add $t5, $s0, $t4                       # Calculate address in display memory
    lw $t9, 0($t5)                              # Load things from $t5 to $t9
    bne $t9, $zero, exit_game               # Check whether intended destination has things, yes, end game, otherwise,
    
    jr $ra                                      # Jump back to caller
    


forbidden:
    jr $ra                                      # Since nothing can be changed, return to caller
    
    
##############################################################################################################################################################################################      
draw_v_unit:                                # Given top left corner's X,Y position, draw a 4*4 square (a v_unit)   
    # Draw 4 4-unit horizontal lines to finish a v_unit square
    addi $sp, $sp, -4                           # Create space in $sp
    sw $ra, 0($sp)                              # Store $ra
    
    li $t2, 4                                   # width of 4 for a v_unit
    
    jal sp_storer_t
    jal draw_horizontal_line
    jal sp_retriever_t
    addi $t1, $t1, 1                            # Move to the next line
    jal sp_storer_t
    jal draw_horizontal_line
    jal sp_retriever_t
    addi $t1, $t1, 1                            # Move to the next line
    jal sp_storer_t
    jal draw_horizontal_line
    jal sp_retriever_t
    addi $t1, $t1, 1                            # Move to the next line
    jal sp_storer_t
    jal draw_horizontal_line
    jal sp_retriever_t
    
    lw $ra, 0($sp)                              # Retrieve $ra
    addi $sp, $sp, 4                            # Release space from sp
    jr $ra
    
    
clear_v_unit:                                # Given top left corner's X, Y position, clear a 4*4 square (a v_unit) $t0 X, $t1 Y.
    addi $sp, $sp, -4                           # Create space in $sp
    sw $ra, 0($sp)                              # Store $ra
    
    li $t2, 4                                   # width of 4 for a v_unit
    
    jal sp_storer_t
    jal clear_horizontal_line
    jal sp_retriever_t
    addi $t1, $t1, 1                            # Move to the next line
    jal sp_storer_t
    jal clear_horizontal_line
    jal sp_retriever_t
    addi $t1, $t1, 1                            # Move to the next line
    jal sp_storer_t
    jal clear_horizontal_line
    jal sp_retriever_t
    addi $t1, $t1, 1                            # Move to the next line
    jal sp_storer_t
    jal clear_horizontal_line
    jal sp_retriever_t
    
    lw $ra, 0($sp)                              # Retrieve $ra
    addi $sp, $sp, 4                            # Release space from sp
    jr $ra


##############################################################################################################################################################################################
draw_virus:
    lw $t6, NUM_VIRUS                           # Initialize loop variant to NUM_VIRUS, stops when painted all ($t6 becomes 0)
    move $s1, $ra                               # Copy $ra as a special location
    j random_virus_loc                          # *: There's no return implemented in random_virus_loc, could potentially result an error
    jr $ra                                      # Jump back to draw_screen
    

jump_to_draw_screen:
    jr $s1


random_virus_loc:                               # **: OUTPUT: 2 LOCATIONs t0, t1, For NUM_VIRUS = 3, Virus Area went from (5, 19) to (36, 58) inclusively. 
    beq $t6, $zero, jump_to_draw_screen         # When all NUM_VIRUS virus was painted, stop drawing and go back, OTHERWISE
    
    # Random select from range(0, 8) --> t8 = x unit
    li $v0, 42                                  # Instruction for random number generator
    li $a0, 0                                   # Pick random number from 0
    li $a1, 8                                   # To 8 (0-7 inclusively), since 4 normal unit is a virus/capsule unit (there're 8 virus unit in a row)
    syscall
    sll $a0, $a0, 2                             # Get Unit by * 4
    addi $t0, $a0, 5                            # Add 5 to random number to get proper x-axis, e.g. (0, 19) to (5, 19), and (x, 19) to (x+5, 19)
    
    # Random select from range(0, 10) --> t9 = y unit
    li $v0, 42                                  # Instruction for random number generator
    li $a0, 0                                   # Pick random number from 0
    li $a1, 10                                  # To 10 (0-9 inclusively), since 4 normal unit is a virus/capsule unit (there're 10 virus unit in a col)
    syscall
    sll $a0, $a0, 2                             # Get Unit by * 4
    addi $t1, $a0, 19                            # Add 5 to random number to get proper x-axis, e.g. (5, 0) to (5, 19), and (5, y) to (5, y+19)
    
    jal paint_virus


paint_virus:                                    #!!!: for now only paints 1 normal unit not a v_unit
    mul $t4, $t1, 64                            # Calculate actual Y unit by * 64
    add $t4, $t4, $t0                           # Add X offset
    sll $t4, $t4, 2                             # Multiply by 4 to get address in t4
    add $t5, $s0, $t4                           # Calculate address in display memory to t5
    lw $t9, 0($t5)                              # Load things from $t5 to $t9
    bne $t9, $zero, random_virus_loc            # Check whether this address has things, yes, jump back to random_virus_loc
    
    addi $sp, $sp, -4                           # Create space in $sp
    sw $ra, 0($sp)                              # Store $ra
    
    jal sp_storer_t                             # Save my temporary registers
    jal random_color                            # Check whether this address is empty, no, assign $s7 with random color
    jal sp_retriever_t                          # Restore my temporary registers
    
    jal draw_v_unit
    addi $t6, $t6, -1                           # And, add 1 to loop variant in draw_virus
    
    lw $ra, 0($sp)                              # Retrieve $ra
    addi $sp, $sp, 4                            # Release space from sp
    jr $ra                                      # Jump back to random_virus_loc
    
    
##############################################################################################################################################################################################
cap_assign_init_col_from_prep:                  # Assign the prep capsule colors to current capsule colors
    la $t5, CAPSULE_COLOR_1                     # Store the address to Label CAPSULE_COLOR_1
    la $t6, CAPSULE_COLOR_2                     # Store the address to Label CAPSULE_COLOR_2
    lw $t7, CAPSULE_PREP_COLOR_1                # Store the value of CAPSULE_PREP_COLOR_1
    lw $t8, CAPSULE_PREP_COLOR_2                # Store the value of CAPSULE_PREP_COLOR_2
    sw $t7, 0($t5)                              # Store Capsule prep color 1 to Capsule color 1
    sw $t8, 0($t6)                              # Store Capsule prep color 2 to Capsule color 2
    
    jr $ra                                      # Jump back to caller
    
    
draw_first_capsule_init:                        # Given t0, t1 as X, Y coordinates
    addi $sp, $sp, -4                           # Create space in $sp
    sw $ra, 0($sp)                              # Store $ra
    
    la $t7, CAPSULE_COLOR_1                     # Store the address of first capsule color
    la $t8, CAPSULE_COLOR_2                     # Store the address of second capsule color
    move $s3, $t0                               # Record this drawn capsule's X coordinate
    move $s4, $t1                               # Record this drawn capsule's Y coordinate
    li $s5, 1                                   # Record the drawn capsule to be horizontal (1)
    
    jal sp_storer_t                             # Save my temporary registers
    jal random_color                            # Assign $s7 with random color
    jal sp_retriever_t                          # Restore my temporary registers

    sw $s7, 0($t7)                              # Store the first color with a random color
    
    jal sp_storer_t                             # Save my temporary registers
    jal draw_v_unit                             # Draw a v_unit of the left part of the capsule
    jal sp_retriever_t                          # Restore my temporary registers
    
    addi $t0, $t0, 4                            # Move to the next v-unit part of capsule
    
    jal sp_storer_t                             # Save my temporary registers
    jal random_color                            # Assign $s7 with random color
    jal sp_retriever_t                          # Restore my temporary registers

    sw $s7, 0($t8)                              # Store the second color with a random color
    
    jal sp_storer_t                             # Save my temporary registers
    jal draw_v_unit                             # Draw a v_unit of the right part of the capsule
    jal sp_retriever_t                          # Restore my temporary registers
    
    lw $ra, 0($sp)                              # Retrieve $ra
    addi $sp, $sp, 4                            # Release space from sp
    jr $ra                                      # Jump back to game loop
    
    
draw_first_capsule_prep:
    addi $sp, $sp, -4                           # Create space in $sp
    sw $ra, 0($sp)                              # Store $ra
    
    la $t7, CAPSULE_PREP_COLOR_1                     # Store the address of first prep capsule color
    la $t8, CAPSULE_PREP_COLOR_2                     # Store the address of second prep capsule color
    
    jal sp_storer_t                             # Save my temporary registers
    jal random_color                            # Assign $s7 with random color
    jal sp_retriever_t                          # Restore my temporary registers

    sw $s7, 0($t7)                              # Load the first prep color with a random color
    
    jal sp_storer_t                             # Save my temporary registers
    jal draw_v_unit                             # Draw a v_unit of capsule on the upper location
    jal sp_retriever_t                          # Restore my temporary registers
    
    addi $t1, $t1, 4                            # Move to the next v-unit part of capsule
    
    jal sp_storer_t                             # Save my temporary registers
    jal random_color                            # Assign $s7 with random color
    jal sp_retriever_t                          # Restore my temporary registers

    sw $s7, 0($t8)                              # Load the second prep color with a random color
    
    jal sp_storer_t                             # Save my temporary registers
    jal draw_v_unit                             # Draw a v_unit of capsule on the lower location
    jal sp_retriever_t                          # Restore my temporary registers
    
    lw $ra, 0($sp)                              # Retrieve $ra
    addi $sp, $sp, 4                            # Release space from sp
    jr $ra                                      # Jump back to game loop


draw_next_capsule:
    beq $s5, 1, draw_capsule_horizontal         # If the previous capsule is horizontal, draw horizontal
    beq $s5, 2, draw_capsule_vertical           # If the previous capsule is vertical, draw vertical
    

add_prep_capsule:                               # Add Initial capsule while checks bottle neck for exit
    addi $sp, $sp, -4                           # Create space in $sp
    sw $ra, 0($sp)                              # Store $ra
    lw $t0, INIT_CAPSULE_X                      # Set capsule x coordinate
    lw $t1, INIT_CAPSULE_Y                      # Set capsule y coordinate
    jal check_bottle_neck                       # Check if the bottle neck is blocked, yes, exit, otherwise,
    jal cap_assign_init_col_from_prep           # Switch the current 2 color of the capsule to the prep ones, and randomly generate new for prep ones
    jal draw_capsule_horizontal                 # Draw a new initial capsule
    
    lw $t0, PREP_CAPSULE_X                      # Set capsule x coordinate
    lw $t1, PREP_CAPSULE_Y                      # Set capsule y coordinate
    jal draw_first_capsule_prep                 # Draw a new prep capsule
    
    lw $ra, 0($sp)                              # Retrieve $ra
    addi $sp, $sp, 4                            # Release space from sp
    jr $ra     
    
    
draw_capsule_horizontal:                        # Given t0, t1 as X, Y coordinates
    addi $sp, $sp, -4                           # Create space in $sp
    sw $ra, 0($sp)                              # Store $ra
    
    move $s3, $t0                               # Record this drawn capsule's X coordinate
    move $s4, $t1                               # Record this drawn capsule's Y coordinate
    li $s5, 1                                   # Record the drawn capsule to be horizontal (1)
    
    lw $s7, CAPSULE_COLOR_1                     # Load Capsule color 1 to $s7
    
    jal sp_storer_t                             # Save my temporary registers
    jal draw_v_unit                             # Draw a v_unit of the left part of the capsule
    jal sp_retriever_t                          # Restore my temporary registers
    
    addi $t0, $t0, 4                            # Move to the next v-unit part of capsule
    
    lw $s7, CAPSULE_COLOR_2                     # Load Capsule color 2 to $s7
    
    jal sp_storer_t                             # Save my temporary registers
    jal draw_v_unit                             # Draw a v_unit of the right part of the capsule
    jal sp_retriever_t                          # Restore my temporary registers
    
    lw $ra, 0($sp)                              # Retrieve $ra
    addi $sp, $sp, 4                            # Release space from sp
    jr $ra                                      # Jump back to game loop
    
    
draw_capsule_vertical:
    addi $sp, $sp, -4                           # Create space in $sp
    sw $ra, 0($sp)                              # Store $ra
    
    move $s3, $t0                               # Record this drawn capsule's X coordinate
    move $s4, $t1                               # Record this drawn capsule's Y coordinate
    li $s5, 2                                   # Record the drawn capsule to be vertical (2)
    
    lw $s7, CAPSULE_COLOR_1                     # Load Capsule color 1 to $s7
    
    jal sp_storer_t                             # Save my temporary registers
    jal draw_v_unit                             # Draw a v_unit of capsule on the upper location
    jal sp_retriever_t                          # Restore my temporary registers
    
    addi $t1, $t1, 4                            # Move to the next v-unit part of capsule
    
    lw $s7, CAPSULE_COLOR_2                     # Load Capsule color 2 to $s7
    
    jal sp_storer_t                             # Save my temporary registers
    jal draw_v_unit                             # Draw a v_unit of capsule on the lower location
    jal sp_retriever_t                          # Restore my temporary registers
    
    lw $ra, 0($sp)                              # Retrieve $ra
    addi $sp, $sp, 4                            # Release space from sp
    jr $ra                                      # Jump back to game loop
 
 
clear_prev_capsule:
    beq $s5, 1, clear_capsule_horizontal        # If the previous capsule is horizontal, clear horizontal
    beq $s5, 2, clear_capsule_vertical          # If the previous capsule is vertical, clear vertical


clear_capsule_horizontal:                      # Given $s3, $s4, the X and Y coordinates of the previous Capsule
    addi $sp, $sp, -4                           # Create space in $sp
    sw $ra, 0($sp)                              # Store $ra
    
    move $t0, $s3                               # Assign t0 with s3's value
    move $t1, $s4                               # Assign t1 with s4's value
    
    jal sp_storer_t                             # Save my temporary registers
    jal clear_v_unit                            # Clear a v_unit of capsule on the left location
    jal sp_retriever_t                          # Restore my temporary registers
    
    addi $t0, $t0, 4                            # Move to the next v-unit part of capsule
    
    jal sp_storer_t                             # Save my temporary registers
    jal clear_v_unit                            # Clear a v_unit of capsule on the right location
    jal sp_retriever_t                          # Restore my temporary registers
    
    lw $ra, 0($sp)                              # Retrieve $ra
    addi $sp, $sp, 4                            # Release space from sp
    jr $ra                                      # Jump back to game loop
    
    
clear_capsule_vertical:                      # Given $s3, $s4, the X and Y coordinates of the previous Capsule
    addi $sp, $sp, -4                           # Create space in $sp
    sw $ra, 0($sp)                              # Store $ra
    
    move $t0, $s3                               # Assign t0 with s3's value
    move $t1, $s4                               # Assign t1 with s4's value
    
    jal sp_storer_t                             # Save my temporary registers
    jal clear_v_unit                            # Clear a v_unit of capsule on the upper location
    jal sp_retriever_t                          # Restore my temporary registers
    
    addi $t1, $t1, 4                            # Move to the next v-unit part of capsule
    
    jal sp_storer_t                             # Save my temporary registers
    jal clear_v_unit                            # Clear a v_unit of capsule on the lower location
    jal sp_retriever_t                          # Restore my temporary registers
    
    lw $ra, 0($sp)                              # Retrieve $ra
    addi $sp, $sp, 4                            # Release space from sp
    jr $ra                                      # Jump back to game loop

    
##############################################################################################################################################################################################
draw_bottle:
    lw $s7, COLOUR_GREY                       # Use grey for bottle edges
    addi $sp, $sp, -4                           # Create space in $sp
    sw $ra, 0($sp)                              # Store $ra
    
    # prep for top left side of the frame
    li $t0, 4                               # Start X position
    li $t1, 6                               # Start Y position
    li $t2, 11                              # Width in units
    jal draw_horizontal_line
    
    # prep for top right side of the frame
    li $t0, 27                              # Start X position
    li $t1, 6                               # Start Y position
    li $t2, 11                              # Width in units
    jal draw_horizontal_line
    
    # left of the bottle neck
    li $t0, 14                              # Start X position for the right side
    li $t1, 2                               # Start Y position just below the top frame
    li $t3, 4                               # Height of the side
    jal draw_vertical_line
    
    # right of the bottle neck
    li $t0, 27                              # Start X position for the right side
    li $t1, 2                               # Start Y position just below the top frame
    li $t3, 4                               # Height of the side
    jal draw_vertical_line
    
    # Right side of the frame
    li $t0, 37                              # Start X position for the right side
    li $t1, 7                               # Start Y position just below the top frame
    li $t3, 52                              # Height of the side
    jal draw_vertical_line
    
    # Bottom side of the frame
    li $t0, 4                               # Start X position for bottom frame
    li $t1, 59                              # Y position for bottom frame
    li $t2, 34                              # Width in units 
    jal draw_horizontal_line
    
    # Left side of the frame
    li $t0, 4                               # Start X position for the left side
    li $t1, 7                               # Start Y position just below the top frame
    li $t3, 52                              # Height of the side
    jal draw_vertical_line
    
    lw $ra, 0($sp)                          # Retrieve $ra
    addi $sp, $sp, 4                        # Release space from sp
    jr $ra                                  # Return to caller


draw_horizontal_line:                                # Given t0 being X, t1 being Y, draw a horizontal line
    mul $t4, $t1, 64                        # Y offset
    add $t4, $t4, $t0                       # Add X offset
    sll $t4, $t4, 2                         # Multiply by 4
    add $t5, $s0, $t4                       # Calculate address in display memory
    sw $s7, 0($t5)                          # Draw color at current unit
    addi $t0, $t0, 1                        # Increment X position
    addi $t2, $t2, -1                       # Decrement width counter
    bnez $t2, draw_horizontal_line           # Loop over width
    jr $ra


draw_vertical_line:                                # Given t0 being X, t1 being Y, draw a vertical line
    mul $t4, $t1, 64                        # Calculate Y offset: Y * 64
    add $t4, $t4, $t0                       # Add X offset
    sll $t4, $t4, 2                         # Multiply by 4
    add $t5, $s0, $t4                       # Calculate address in display memory
    sw $s7, 0($t5)                          # Draw color at current unit
    addi $t1, $t1, 1                        # Increment Y position
    addi $t3, $t3, -1                       # Decrement height counter
    bnez $t3, draw_vertical_line         # Loop over height
    jr $ra
    

clear_horizontal_line:                                # Given t0 being X, t1 being Y, clear a horizontal line
    mul $t4, $t1, 64                        # Y offset
    add $t4, $t4, $t0                       # Add X offset
    sll $t4, $t4, 2                         # Multiply by 4
    add $t5, $s0, $t4                       # Calculate address in display memory
    sw $zero, 0($t5)                          # Clear the current unit
    addi $t0, $t0, 1                        # Increment X position
    addi $t2, $t2, -1                       # Decrement width counter
    bnez $t2, clear_horizontal_line           # Loop over width
    jr $ra
    
    
##############################################################################################################################################################################################
key_pressed:                                    # Check key has been pressed
    lw $t0, ADDR_KBRD                           # $t0 = base address for keyboard
    lw $t8, 0($t0)                              # Load first word from keyboard
    beq $t8, 1, keyboard_input                  # If first word 1, key is pressed, check which key pressed
    jr $ra                                      # Jump back to game_loop


keyboard_input:                                 # A key is pressed
    lw $a0, 4($t0)                              # Load second word from keyboard
    beq $a0, 0x71, exit_game                    # Check if the key q was pressed
    beq $a0, 0x77, respond_to_W                 # Check if the key w was pressed
    beq $a0, 0x61, respond_to_A                 # Check if the key a was pressed
    beq $a0, 0x73, respond_to_S                 # Check if the key s was pressed
    beq $a0, 0x64, respond_to_D                 # Check if the key d was pressed
    beq $a0, 0x70, respond_to_P                 # Check if the key p was pressed
    beq $a0, 0x6C, switch_to_low_mode           # Check if the key 'l' was pressed (lowercase 'l')
    beq $a0, 0x6D, switch_to_medium_mode        # Check if the key 'm' was pressed (lowercase 'm')
    beq $a0, 0x68, switch_to_high_mode          # Check if the key 'h' was pressed (lowercase 'h')
    jr $ra                                      # Jump back to game_loop if no special key is detected


respond_to_W:
    addi $sp, $sp, -4                           # Create space in $sp
    sw $ra, 0($sp)                              # Store $ra from keyboard_input
    
    move $t0, $s3                              # Update $t0 for the X coordinate for the previous capsule
	move $t1, $s4                              # Update $t1 for the Y coordinate for the previous capsule
	
    jal clear_prev_capsule                           # Clear the previous capsule
    li $t7, 0                                 # Use $t7 to store intended horizontal change location
	jal check_collision_rotation            # Check & Update whether intended change location is available
	jal draw_next_capsule                      # Draw the next capsule
	
    lw $ra, 0($sp)                              # Retrieve $ra
    addi $sp, $sp, 4                            # Release space from sp
    jr $ra                                      # Jump back to game_loop
	
	
respond_to_A:
    addi $sp, $sp, -4                           # Create space in $sp
    sw $ra, 0($sp)                              # Store $ra from keyboard_input
    
	move $t0, $s3                              # Update $t0 for the X coordinate for the previous capsule
	move $t1, $s4                              # Update $t1 for the Y coordinate for the previous capsule
	
	move $t8, $t0                               # Record the current x coordinate $t0
	
    jal clear_prev_capsule                           # Clear the previous capsule
	li $t7, -4                                 # Use $t7 to store intended horizontal change location
	jal check_collision_horizontal            # Check & Update whether intended change location is available
	jal draw_next_capsule                      # Draw the next capsule
	
	lw $ra, 0($sp)                             # Retrieve $ra
    addi $sp, $sp, 4                            # Release space from sp
    jr $ra                                      # Jump back to game_loop
	
	
respond_to_S:
	addi $sp, $sp, -4                           # Create space in $sp
    sw $ra, 0($sp)                              # Store $ra from keyboard_input
    
	move $t0, $s3                              # Update $t0 for the X coordinate for the previous capsule
	move $t1, $s4                              # Update $t1 for the Y coordinate for the previous capsule
	
	move $t8, $t1                               # Record the current y coordinate $t1
	
    jal clear_prev_capsule                           # Clear the previous capsule
	li $t7, 4                                 # Use $t7 to store intended vertical change location
	jal check_collision_vertical            # Check & Update whether intended change location is available
	jal draw_next_capsule                       # Draw the next capsule
	
	lw $ra, 0($sp)                             # Retrieve $ra
    addi $sp, $sp, 4                            # Release space from sp
    jr $ra                                      # Jump back to game_loop
	
	
respond_to_D:
	addi $sp, $sp, -4                           # Create space in $sp
    sw $ra, 0($sp)                              # Store $ra from keyboard_input
    
	move $t0, $s3                              # Update $t0 for the X coordinate for the previous capsule
	move $t1, $s4                              # Update $t1 for the Y coordinate for the previous capsule
	
	move $t8, $t0                               # Record the current x coordinate $t0
	
    jal clear_prev_capsule                           # Clear the previous capsule
	li $t7, 4                                 # Use $t7 to store intended horizontal change location
	jal check_collision_horizontal            # Check & Update whether intended change location is available
	jal draw_next_capsule                       # Draw the next capsule
	
	lw $ra, 0($sp)                             # Retrieve $ra
    addi $sp, $sp, 4                            # Release space from sp
    jr $ra                                      # Jump back to game_loop
    
    
respond_to_P:
    addi $sp, $sp, -4
    sw $ra, 0($sp)
    
    lw $t0, IS_PAUSED
    xori $t0, $t0, 1                           # Toggle between 0 and 1
    sw $t0, IS_PAUSED
    
    bnez $t0, save_gravity                     # If now paused, save gravity
    j restore_gravity                          # If now unpaused, restore gravity
	
	
exit_game:
	li $v0, 10                                  # Quit gracefully
	syscall


##############################################################################################################################################################################################
sp_storer_t:
    addi $sp, $sp, -4                           # Create space in $sp
    sw $t9, 0($sp)                              # Store $t9
    addi $sp, $sp, -4                           # Create space in $sp
    sw $t8, 0($sp)                              # Store $t8
    addi $sp, $sp, -4                           # Create space in $sp
    sw $t7, 0($sp)                              # Store $t7
    addi $sp, $sp, -4                           # Create space in $sp
    sw $t6, 0($sp)                              # Store $t6
    addi $sp, $sp, -4                           # Create space in $sp
    sw $t5, 0($sp)                              # Store $t5
    addi $sp, $sp, -4                           # Create space in $sp
    sw $t4, 0($sp)                              # Store $t4
    addi $sp, $sp, -4                           # Create space in $sp
    sw $t3, 0($sp)                              # Store $t3
    addi $sp, $sp, -4                           # Create space in $sp
    sw $t2, 0($sp)                              # Store $t2
    addi $sp, $sp, -4                           # Create space in $sp
    sw $t1, 0($sp)                              # Store $t1
    addi $sp, $sp, -4                           # Create space in $sp
    sw $t0, 0($sp)                              # Store $t0
    jr $ra
    
    
sp_retriever_t:
    lw $t0, 0($sp)                              # Retrieve $t0
    addi $sp, $sp, 4                            # Release space from sp
    lw $t1, 0($sp)                              # Retrieve $t1
    addi $sp, $sp, 4                            # Release space from sp
    lw $t2, 0($sp)                              # Retrieve $t2
    addi $sp, $sp, 4                            # Release space from sp
    lw $t3, 0($sp)                              # Retrieve $t3
    addi $sp, $sp, 4                            # Release space from sp
    lw $t4, 0($sp)                              # Retrieve $t4
    addi $sp, $sp, 4                            # Release space from sp
    lw $t5, 0($sp)                              # Retrieve $t5
    addi $sp, $sp, 4                            # Release space from sp
    lw $t6, 0($sp)                              # Retrieve $t6
    addi $sp, $sp, 4                            # Release space from sp
    lw $t7, 0($sp)                              # Retrieve $t7
    addi $sp, $sp, 4                            # Release space from sp
    lw $t8, 0($sp)                              # Retrieve $t8
    addi $sp, $sp, 4                            # Release space from sp
    lw $t9, 0($sp)                              # Retrieve $t9
    addi $sp, $sp, 4                            # Release space from sp
    jr $ra
    
    
##############################################################################
# Features
##############################################################################
gravity:                                        # Easy feature 1: implement gravity, so that each second that passes will automatically move the capsule down one row.
    addi $sp, $sp, -4                           # Create space in $sp
    sw $ra, 0($sp)                              # Store $ra from keyboard_input
    move $t0, $s3                              # Update $t0 for the X coordinate for the previous capsule
	move $t1, $s4                              # Update $t1 for the Y coordinate for the previous capsule
	
    jal clear_prev_capsule                           # Clear the previous capsule
	li $t7, 4                                  # See if we can keep moving down by 1 row
    jal check_collision_vertical                # Check If below collides
    jal draw_next_capsule                       # If not, draw next capsule
    
    lw $ra, 0($sp)                             # Retrieve $ra
    addi $sp, $sp, 4                            # Release space from sp
    jr $ra

music:                                          # Hard feature 5: play the Dr. Mario’s theme music in the background while playing the game.
    # TODO: 
    
# mode:                                           # Easy feature 3: allow the player to select Easy, Medium or Hard mode before the game, and increase the number of viruses and the game speed based on their choice.
    switch_to_low_mode:
        lw $t0, GRAVITY_THRESHOLD_LOW    # Load low gravity threshold value
        sw $t0, CURRENT_GRAVITY_THRESHOLD # Set it as the current gravity threshold
        j mode_change_done

    switch_to_medium_mode:
        lw $t0, GRAVITY_THRESHOLD_MEDIUM # Load medium gravity threshold value
        sw $t0, CURRENT_GRAVITY_THRESHOLD # Set it as the current gravity threshold
        j mode_change_done
    
    switch_to_high_mode:
        lw $t0, GRAVITY_THRESHOLD_HIGH   # Load high gravity threshold value
        sw $t0, CURRENT_GRAVITY_THRESHOLD # Set it as the current gravity threshold
    
    mode_change_done:
        # Optional: Print message to indicate the mode has changed
        la $a0, MSG_MODE_CHANGED         # Load address of "Mode changed" message
        li $v0, 4                        # System call for print string
        syscall
        jr $ra                           # Jump back to caller


    
# game_over:                                      # Easy feature 4: when the player has reached the ”game over” condition, display a Game Over screen in pixels on the screen. Restart the game if a “retry” option is chosen by the player. Retry should start a brand new game (no state is retained from previous attempts).



# GAME PAUSE HLPERS                             # Easy feature 6: if the player presses the keyboard key p, display a ”Paused” message on screen until they press p a second time, at which point the original game will resume.
      save_gravity:
        sw $s1, last_gravity_count
        j pause_done
        
      pause_done:
        jal draw_pause_indicator                   # Draw indicator after state change
    
        lw $ra, 0($sp)
        addi $sp, $sp, 4
        jr $ra
    
     draw_pause_indicator:
        addi $sp, $sp, -4
        sw $ra, 0($sp)
        
        li $t0, 55                          # X coordinate
        li $t1, 4                          # Y coordinate
        li $t3, 5                          # Height counter
        
        lw  $t2, IS_PAUSED
        bnez $t2, draw_pause_square
    
    draw_pause_square:
        lw $s7, COLOUR_WHITE
    
    clear_game_state_indicator:
        lw $t7, COLOUR_BLACK
        
    pause_square_loop:
        li $t2, 5                           # Reset width to 5 for each line
        move $t8, $t0                       # Save starting X position
        jal draw_horizontal_line
        move $t0, $t8                       # Restore X position
        addi $t1, $t1, 1                    # Next row
        addi $t3, $t3, -1
        bnez $t3, pause_square_loop
        j indicator_done
    
    indicator_done:
        lw $ra, 0($sp)
        addi $sp, $sp, 4
        jr $ra
        
    restore_gravity: 
        lw $s1, last_gravity_count
    
    game_loop_sleep:
        li $v0, 32                                  
        li $a0, 16                                  
        syscall
        j game_loop
game_config:                                    # Hard feature 4: create menu screens for things like level selection, number of viruses, speed, a score board of high scores, etc (assumes you have completed at least one of those hard features).
    # TODO: configure game settings.
    jr $ra                                      # Jump back to main
    
    
##########################################################################################################################
tests:
    # Parameters for testing purposes.

End_of_the_World:
    li $v0, 10                                  # Quit gracefully
	syscall