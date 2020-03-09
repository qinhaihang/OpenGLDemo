attribute vec4 a_Position;

void main() {
    gl_Position = a_Position;
    gl_PointSize = 10.0; //设置点的大小，必须是float类型
}
