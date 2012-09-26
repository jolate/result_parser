# Gnuplot Script
# To generate: C-x p
################################################################################
# This program by L A Ferres is in the public domain and freely copyable.
# (or, if there were problems, in the errata  --- see
#    http://www.udec.cl/~leo/programs.html)
# If you find any bugs, please report them immediately to: leo@inf.udec.cl
################################################################################
#
# FILE NAME: plingeling_6cores.gnuplot
#
# PURPOSE:
#
# FILE(S) GENERATING THIS DATA:
#
# CREATION DATE: 2012-02-02-13:36; MODIFIED:
#
################################################################################
#
# General options for Azu graphs
# They are inserted in Latex files in a picture environment
#
set terminal postscript portrait "Times-Roman" 10
set terminal postscript enhanced
set output "SNSStime.eps"
set border
set size square 0.5,0.5 # percent of original (50% of its size here)
unset time # unset for no time
set lmargin 10
set bmargin -1
set rmargin 2
set tmargin 1
set bars 1

# Details for this particular plot
set key left # legend to the left (bottom is also an option)
unset logscale y  #set for logarithmic scale y (add x for log x)
set xlabel "# of threads" # XAxis title
set ylabel "Performance decay (time %)" # YAxis title
set key at 0,-20
set key spacing 0.6
set border 3 lw 0.5
set xtics nomirror
set ytics nomirror
set ytics 0,10,100
set yrange [0:100]
set title "Shared-None-Same-Search Time Decay"

plot "SNSStime.dat" using 1:((($2/82.6)*100)-100):(((($2/82.6)*100)-100)*($3/100)) lt 1 lw 0.5 pt 7 ps 0.3 title '{/Times=6 manol-pipe-c10b}' with yerrorlines, \
"SNSStime.dat" using 1:((($4/255.0)*100)-100):(((($4/255.0)*100)-100)*($5/100)) lt 2 lw 0.5 pt 7 ps 0.3 title '{/Times=6 manol-pipe-c6bid_i}' with yerrorlines, \
"SNSStime.dat" using 1:((($6/298.9)*100)-100):(((($6/298.9)*100)-100)*($7/100)) lt 3 lw 0.5 pt 7 ps 0.3 title '{/Times=6 manol-pipe-c6nidw_i}' with yerrorlines, \
"SNSStime.dat" using 1:((($8/70.9)*100)-100):(((($8/70.9)*100)-100)*($9/100)) lt 4 lw 0.5 pt 7 ps 0.3 title '{/Times=6 manol-pipe-c7idw}' with yerrorlines, \
"SNSStime.dat" using 1:((($10/60.2)*100)-100):(((($10/60.2)*100)-100)*($11/100)) lt 5 lw 0.5 pt 7 ps 0.3 title '{/Times=6 manol-pipe-cha05-113}' with yerrorlines, \
"SNSStime.dat" using 1:((($12/1146.8)*100)-100):(((($12/1146.8)*100)-100)*($13/100)) lt 6 lw 0.5 pt 7 ps 0.3 title '{/Times=6 anbul-dated-5-15-u}' with yerrorlines, \
"SNSStime.dat" using 1:((($14/1569.5)*100)-100):(((($14/1569.5)*100)-100)*($15/100)) lt 7 lw 0.5 pt 7 ps 0.3 title '{/Times=6 ibm-2002-31_1r3-k30}' with yerrorlines, \
"SNSStime.dat" using 1:((($16/966.9)*100)-100):(((($16/966.9)*100)-100)*($17/100)) lt 8 lw 0.5 pt 7 ps 0.3 title '{/Times=6 post-c32s-gcdm16-22}' with yerrorlines

#plot "data.txt" using 2:xticlabels(1) title '' with linespoints #labels in 1
