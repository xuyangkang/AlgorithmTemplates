package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

var scanner *bufio.Scanner = bufio.NewScanner(os.Stdin)
var writer *bufio.Writer = bufio.NewWriter(os.Stdout)

func ReadString() string {
    scanner.Scan()
    return scanner.Text()
}

func ReadFloat64() float64 {
    f64, _ := strconv.ParseFloat(ReadString(), 64)
    return f64
}

func ReadInt64() int64 {
	i64, _ := strconv.ParseInt(ReadString(), 10, 0)
	return i64
}

func ReadInt() int {
    return int(ReadInt64())
}

func main() {
	defer writer.Flush()
	scanner.Split(bufio.ScanWords)
	fmt.Fprintln(writer, "Hello, 世界！")
}
